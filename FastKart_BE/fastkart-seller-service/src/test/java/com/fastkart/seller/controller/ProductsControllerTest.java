package com.fastkart.seller.controller;

import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;
import com.fastkart.seller.model.ProductDetailsResponse;
import com.fastkart.seller.model.ProductsResponse;
import com.fastkart.seller.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@ExtendWith(MockitoExtension.class)
class ProductsControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductsController productsController;

    @Test
    void getProductsTest(){
        Mockito.when(productService.getProducts(any(),anyInt(),anyInt())).thenReturn(new ProductsResponse());
        assertNotNull(productsController.getProducts(1,5,"tokenTest"));
    }
    @Test
    void getProductByProductIdTest(){
        Mockito.when(productService.getProductByProductId(anyInt())).thenReturn(new ProductDetailsResponse());
        assertNotNull(productsController.getProductByProductId("tokenTest", 123));
    }
    @Test
    void addProductTest(){
        Mockito.when(productService.addProduct(any(), any())).thenReturn(new AddProductResponse());
        assertNotNull(productsController.addProduct(new AddProductRequest(), "tokenTest"));
    }
    /*@Test
    void processProductCSVTest() throws Exception {
        Mockito.when(productService.processProductCSV()).thenReturn(new AddProductResponse());
        Assert.notNull(productsController.processProductCSV(new MultipartFile() {
        }));
    }*/
}
