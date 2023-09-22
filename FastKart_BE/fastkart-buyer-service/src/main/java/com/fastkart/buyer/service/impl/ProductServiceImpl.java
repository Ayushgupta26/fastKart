package com.fastkart.buyer.service.impl;

import com.fastkart.buyer.config.AuthConfig;
import com.fastkart.buyer.constants.CommonConstants;
import com.fastkart.buyer.entity.Product;
import com.fastkart.buyer.entity.ProductBid;
import com.fastkart.buyer.exception.ProductException;
import com.fastkart.buyer.model.AddBidRequest;
import com.fastkart.buyer.model.AddBidResponse;
import com.fastkart.buyer.model.ProductsResponse;
import com.fastkart.buyer.repository.ProductBidRepository;
import com.fastkart.buyer.repository.ProductRepository;
import com.fastkart.buyer.service.ProductService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductBidRepository productBidRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private AuthConfig authConfig;

    public ProductsResponse getAllProducts(Integer pageNumber, Integer size) {
        Page<Product> products = null;
        try {
            log.info("start of get all products method");
            products = productRepository.findAll(PageRequest.of(pageNumber, size, Sort.by(Sort.Direction.DESC, "listedDateTime")));
            log.info("end of get all products method");
        } catch (Exception ex) {
            log.error("exception in add bid to product : {}", ex.getMessage());
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

    public AddBidResponse addProductToBid(AddBidRequest addBidRequest, String authorization) {
        AddBidResponse addBidResponse = new AddBidResponse();
        log.info("start of add bid to product method");
        try {
            Optional<Product> product = productRepository.findById(addBidRequest.getProductId());
            if (product.isEmpty()) {
                log.error("Product does not exist with given product Id");
                throw new ProductException("Product does not exist with given product Id");
            }
            if (addBidRequest.getBidAmount() < 0 || addBidRequest.getBidAmount() < product.get().getMinBid()) {
                log.error("Bid should be greater than the minimum bid of product");
                throw new ProductException("Bid amount too low, minimum bid is ".concat(String.valueOf(product.get().getMinBid())));
            }
            String referenceToken = authorization.split(CommonConstants.SPACE)[1];
            Claims claims = authConfig.getAllClaimsFromToken(referenceToken);
            ProductBid productBid = new ProductBid();
            productBid.setBidAmount(addBidRequest.getBidAmount());
            productBid.setBuyerUserName(claims.getSubject());
            productBid.setProduct(product.get());
            productBid.setBidDateTime(LocalDateTime.now());
            productBidRepository.save(productBid);
            log.info("bid added success fully to product with productId : {}", addBidRequest.getProductId());
            addBidResponse.setResponseMessage(CommonConstants.PRODUCT_BID_ADDED_SUCCESSFULLY);
            log.info("end of add bid to product method");
        } catch (Exception ex) {
            log.error("exception in add bid to product : {}", ex.getMessage());
            throw new ProductException(ex.getMessage());
        }
        return addBidResponse;
    }
}
