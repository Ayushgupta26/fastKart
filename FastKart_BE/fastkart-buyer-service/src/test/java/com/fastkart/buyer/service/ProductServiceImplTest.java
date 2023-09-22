package com.fastkart.buyer.service;

import com.fastkart.buyer.config.AuthConfig;
import com.fastkart.buyer.entity.Product;
import com.fastkart.buyer.entity.ProductBid;
import com.fastkart.buyer.exception.ProductException;
import com.fastkart.buyer.model.AddBidRequest;
import com.fastkart.buyer.repository.ProductBidRepository;
import com.fastkart.buyer.repository.ProductRepository;
import com.fastkart.buyer.service.impl.ProductServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    ProductBidRepository productBidRepository;

    @Mock
    ProductRepository productRepository;
    @InjectMocks
    ProductServiceImpl productService;
    @Mock
    private AuthConfig authConfig;

    @Test
    void getAllProductsTest_success() {
        Page<Product> page = Mockito.mock(Page.class);
        Mockito.when(productRepository.findAll((Pageable) any())).thenReturn(page);
        assertNotNull(productService.getAllProducts(1,2));
    }

    @Test
    void getAllProductsTest_exception() {
        Mockito.when(productRepository.findAll()).thenThrow(new ProductException());
        assertThrows(ProductException.class, () -> productService.getAllProducts(1,2));
    }

    @Test
    void addBidToProductTest_success() {
        Product product = new Product();
        product.setMinBid(50);
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(product));
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(authConfig.getAllClaimsFromToken(any())).thenReturn(claims);
        Mockito.when(productBidRepository.save(any())).thenReturn(new ProductBid());
        AddBidRequest addBidRequest = new AddBidRequest();
        addBidRequest.setBidAmount(100);
        assertNotNull(productService.addProductToBid(addBidRequest, "Bearer token"));
    }

    @Test
    void addBidToProductTest_ProductNotFoundException() {
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ProductException.class, () -> productService.addProductToBid(new AddBidRequest(), "Bearer token"));
    }

    @Test
    void addBidToProductTest_BidAmountException() {
        Product product = new Product();
        product.setMinBid(50);
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(product));
        AddBidRequest addBidRequest = new AddBidRequest();
        addBidRequest.setBidAmount(10);
        assertThrows(ProductException.class, () -> productService.addProductToBid(new AddBidRequest(), "Bearer token"));
    }
}
