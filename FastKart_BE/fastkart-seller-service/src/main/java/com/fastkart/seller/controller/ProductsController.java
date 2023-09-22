package com.fastkart.seller.controller;

import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;
import com.fastkart.seller.model.ProductDetailsResponse;
import com.fastkart.seller.model.ProductsResponse;
import com.fastkart.seller.service.ProductService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/seller")
public class ProductsController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ProductsResponse> getProducts(@RequestParam(defaultValue = "0") final Integer pageNumber,
                                                        @RequestParam(defaultValue = "5") final Integer size,
                                                        @RequestHeader(name = "AUTHORIZATION", required = true) String authorization){
        ProductsResponse productsResponse = productService.getProducts(authorization, pageNumber, size);
        return new ResponseEntity<>(productsResponse, HttpStatus.OK);
    }

    @GetMapping("/productById/{productId}")
    public ResponseEntity<ProductDetailsResponse> getProductByProductId(@RequestHeader(name = "AUTHORIZATION", required = true) String authorization,
                                                                        @PathVariable("productId") int productId){
        ProductDetailsResponse product = productService.getProductByProductId(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/add/product")
    public ResponseEntity<AddProductResponse> addProduct(@Valid @RequestBody AddProductRequest addProductRequest,
                                                         @RequestHeader(name = "AUTHORIZATION", required = true) String authorization){
        return new ResponseEntity<>(productService.addProduct(addProductRequest, authorization), HttpStatus.OK);
    }

    @PostMapping("/process/csv/data")
    public ResponseEntity<AddProductResponse> processProductCSV(@RequestParam("file") MultipartFile file,
                                                                @RequestHeader(name = "AUTHORIZATION", required = true) String authorization) throws Exception {
        return new ResponseEntity<>(productService.processProductCSV(file, authorization), HttpStatus.OK);
    }
}
