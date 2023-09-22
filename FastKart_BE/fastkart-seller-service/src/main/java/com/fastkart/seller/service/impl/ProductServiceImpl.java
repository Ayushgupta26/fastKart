package com.fastkart.seller.service.impl;

import com.fastkart.seller.config.AuthConfig;
import com.fastkart.seller.constants.CommonConstants;
import com.fastkart.seller.entity.Product;
import com.fastkart.seller.entity.ProductBid;
import com.fastkart.seller.exception.ProductException;
import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;
import com.fastkart.seller.model.ProductDetailsResponse;
import com.fastkart.seller.model.ProductsResponse;
import com.fastkart.seller.repository.ProductRepository;
import com.fastkart.seller.service.ProductService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job job;

    @Override
    public ProductsResponse getProducts(String authorization, Integer pageNumber, Integer size) {
        Page<Product> products = null;
        log.info("start of getProducts by seller");
        String referenceToken = authorization.split(CommonConstants.SPACE)[1];
        try {
            Claims claims = authConfig.getAllClaimsFromToken(referenceToken);
            products = productRepository.findBySellerName(claims.getSubject(), PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "listedDateTime")));
            log.info("end of getProducts by seller");
        } catch (Exception ex) {
            log.error("exception in get products : {}", ex.getMessage());
            throw new ProductException(ex.getMessage());
        }
        return convertToResponse(products);
    }

    private ProductsResponse convertToResponse(final Page<Product> page) {
        ProductsResponse productsResponse = new ProductsResponse();
        List<Product> products = page.getContent();
        productsResponse.setProducts(products);
        productsResponse.setCurrentPage(page.getNumber());
        productsResponse.setTotalItems(page.getTotalElements());
        productsResponse.setTotalPages(page.getTotalPages());
        productsResponse.getProducts().stream().filter(product -> !product.getProductBids().isEmpty()).forEach(product -> product.setMaxBidByBuyer(product.getProductBids().stream().max(Comparator.comparing(ProductBid::getBidAmount)).get().getBidAmount()));
        return productsResponse;
    }

    public ProductDetailsResponse getProductByProductId(int productId) {
        log.info("start of getProductByProductId method");
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        Optional<Product> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            log.error("Product does not exist with given product Id");
            throw new ProductException("Product does not exist with given product Id");
        }
        Product productDetails = product.get();
        BeanUtils.copyProperties(productDetails, productDetailsResponse);
        if (!productDetails.getProductBids().isEmpty()) {
            productDetailsResponse.setMinBidByBuyer(productDetails.getProductBids().stream().min(Comparator.comparing(ProductBid::getBidAmount)).get().getBidAmount());
            productDetailsResponse.setMaxBidByBuyer(productDetails.getProductBids().stream().max(Comparator.comparing(ProductBid::getBidAmount)).get().getBidAmount());
        }
        productDetails.getProductBids().sort(Comparator.comparing(ProductBid::getBidDateTime).reversed());
        log.info("end of getProductByProductId method");
        return productDetailsResponse;
    }

    public AddProductResponse addProduct(AddProductRequest addProductRequest, String authorization) {
        AddProductResponse addProductResponse = new AddProductResponse();
        log.info("start of add product method");
        String referenceToken = authorization.split(CommonConstants.SPACE)[1];
        try {
            Claims claims = authConfig.getAllClaimsFromToken(referenceToken);
            Product product = new Product();
            product.setProductName(addProductRequest.getProductName());
            product.setProductCategory(addProductRequest.getProductCategory());
            product.setProductDescription(addProductRequest.getProductDescription());
            product.setSellerName(claims.getSubject());
            product.setListedDateTime(LocalDateTime.now());
            product.setMinBid(addProductRequest.getMinBid());
            Product addedProduct = productRepository.save(product);
            addProductResponse.setResponseMessage(CommonConstants.PRODUCT_ADDED_SUCCESSFULLY);
            addProductResponse.setProductId(addedProduct.getProductId());
            log.info("end of add product method");
        } catch (Exception ex) {
            log.error("exception in add product : {}", ex.getMessage());
            throw new ProductException(ex.getMessage());
        }
        return addProductResponse;
    }

    @Override
    public AddProductResponse processProductCSV(MultipartFile file, String authorization) throws Exception {
        AddProductResponse addProductResponse = new AddProductResponse();
        try {
            if (!file.isEmpty() && file.getOriginalFilename().endsWith(".csv")) {
                Path tempFile = Files.createTempFile("temp-", ".csv");
                file.transferTo(tempFile.toFile());
                String referenceToken = authorization.split(CommonConstants.SPACE)[1];
                Claims claims = authConfig.getAllClaimsFromToken(referenceToken);
                JobParameters jobParameters = new JobParametersBuilder()
                        .addLong("startAt", System.currentTimeMillis())
                        .addString("inputFile", tempFile.toString())
                        .addString("sellerName", claims.getSubject()).toJobParameters();

                final JobExecution execution = jobLauncher.run(job, jobParameters);
                final ExitStatus status = execution.getExitStatus();
                if (ExitStatus.COMPLETED.getExitCode().equalsIgnoreCase(status.getExitCode())) {
                    log.info("Job Completed");
                    addProductResponse.setResponseMessage("Processed");
                } else {
                    log.info("Job is not completed");
                    addProductResponse.setResponseMessage("Not Completed");
                }
            } else {
                throw new ProductException("Invalid file format.");
            }
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | FlatFileParseException e) {
            log.error("Exception in running the job : {}", e.getMessage());
            throw new ProductException(e.getMessage());
        }
        return addProductResponse;
    }
}
