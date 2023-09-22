package com.fastkart.seller.service;

import com.fastkart.seller.config.AuthConfig;
import com.fastkart.seller.entity.Product;
import com.fastkart.seller.exception.ProductException;
import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.repository.ProductRepository;
import com.fastkart.seller.service.impl.ProductServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private AuthConfig authConfig;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    @Test
    void getProductsTest_success() {
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(authConfig.getAllClaimsFromToken(any())).thenReturn(claims);
        Page<Product> page = Mockito.mock(Page.class);
        Mockito.when(productRepository.findBySellerName(any(),any())).thenReturn(page);
        assertNotNull(productService.getProducts("Bearer token",1,5));
    }

    @Test
    void getProductsTest_Exception() {
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(authConfig.getAllClaimsFromToken(any())).thenReturn(claims);
        Mockito.when(productRepository.findBySellerName(any(),any())).thenThrow(new ProductException());
        assertThrows(ProductException.class, () -> productService.getProducts("Bearer token",1,5));
    }

    @Test
    void getProductByProductIdTest_success(){
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.of(new Product()));
        assertNotNull(productService.getProductByProductId(1));
    }

    @Test
    void getProductByProductIdTest_exception(){
        Mockito.when(productRepository.findById(any())).thenReturn(Optional.empty());
        assertThrows(ProductException.class, () -> productService.getProductByProductId(1));
    }

    @Test
    void addProductTest_success(){
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(authConfig.getAllClaimsFromToken(any())).thenReturn(claims);
        Mockito.when(productRepository.save(any())).thenReturn(new Product());
        assertNotNull(productService.addProduct(new AddProductRequest(),"Bearer token"));
    }

    @Test
    void addProductTest_exception(){
        Claims claims = Mockito.mock(Claims.class);
        Mockito.when(authConfig.getAllClaimsFromToken(any())).thenReturn(claims);
        Mockito.when(productRepository.save(any())).thenThrow(new ProductException(""));
        assertThrows(ProductException.class, () -> productService.addProduct(new AddProductRequest(),"Bearer token"));
    }

}
