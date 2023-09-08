package com.fastkart.seller.service.impl;

import com.fastkart.seller.config.AuthConfig;
import com.fastkart.seller.entity.Product;
import com.fastkart.seller.exception.ProductException;
import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;
import com.fastkart.seller.repository.ProductRepository;
import com.fastkart.seller.service.ProductService;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AuthConfig authConfig;

    public List<Product> getProducts(String authorization) {
        List<Product> products = new ArrayList<>();
        String referenceToken = authorization.split(" ")[1];
        try {
            Claims claims = authConfig.getAllClaimsFromToken(referenceToken);
            products = productRepository.findBySellerName(claims.getSubject());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return products;
    }

    public Product getProductByProductId(int productId){
        Optional<Product> product = productRepository.findById(productId);
        if(!product.isPresent()){
            throw new ProductException("Product does not exist with given product Id");
        }
        return product.get();
    }

    public AddProductResponse addProduct(AddProductRequest addProductRequest, String authorization) {
        AddProductResponse addProductResponse = new AddProductResponse();
        String referenceToken = authorization.split(" ")[1];
        try {
            Claims claims = authConfig.getAllClaimsFromToken(referenceToken);
            Product product = new Product();
            product.setProductName(addProductRequest.getProductName());
            product.setProductCategory(addProductRequest.getProductCategory());
            product.setProductDescription(addProductRequest.getProductDescription());
            product.setSellerName(claims.getSubject());
            Product addedProduct = productRepository.save(product);
            addProductResponse.setResponseMessage("Product added");
            addProductResponse.setProductId(addedProduct.getProductId());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new ProductException(ex.getMessage());
        }
        return addProductResponse;
    }
}
