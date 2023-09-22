package com.fastkart.buyer.controller;

import com.fastkart.buyer.model.AddBidRequest;
import com.fastkart.buyer.model.AddBidResponse;
import com.fastkart.buyer.model.ProductsResponse;
import com.fastkart.buyer.service.ProductService;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getAllProductsTest(){
        Mockito.when(productService.getAllProducts(any(),any())).thenReturn(new ProductsResponse());
        assertNotNull(productController.getAllProducts(1,2));
    }

    @Test
    void authenticateAndGetTokenTest(){
        Mockito.when(productService.addProductToBid(any(),any())).thenReturn(new AddBidResponse());
        assertNotNull(productController.addBidToProduct(new AddBidRequest(), "token"));
    }
}
