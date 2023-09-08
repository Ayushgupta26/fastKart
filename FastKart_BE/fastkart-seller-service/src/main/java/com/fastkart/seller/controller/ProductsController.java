package com.fastkart.seller.controller;

import com.fastkart.seller.entity.Product;
import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;
import com.fastkart.seller.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class ProductsController {

    @Autowired
    private ProductServiceImpl productService;

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(@RequestHeader(name = "AUTHORIZATION", required = true) String authorization){
        List<Product> products = productService.getProducts(authorization);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/productById/{productId}")
    public ResponseEntity<Product> getProductById(@RequestHeader(name = "AUTHORIZATION", required = true) String authorization,
                                                  @PathVariable("productId") int productId){
        Product product = productService.getProductByProductId(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add/product")
    public ResponseEntity<AddProductResponse> addProduct(@Valid @RequestBody AddProductRequest addProductRequest,
                                                         @RequestHeader(name = "AUTHORIZATION", required = true) String authorization){
        return new ResponseEntity<>(productService.addProduct(addProductRequest, authorization), HttpStatus.OK);
    }
}
