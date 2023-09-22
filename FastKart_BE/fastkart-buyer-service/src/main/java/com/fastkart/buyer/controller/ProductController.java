package com.fastkart.buyer.controller;

import com.fastkart.buyer.model.AddBidRequest;
import com.fastkart.buyer.model.AddBidResponse;
import com.fastkart.buyer.model.ProductsResponse;
import com.fastkart.buyer.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/buyer")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<ProductsResponse> getAllProducts(@RequestParam(defaultValue = "0") final Integer pageNumber,
                                                           @RequestParam(defaultValue = "5") final Integer size) {
        return new ResponseEntity<>(productService.getAllProducts(pageNumber, size), HttpStatus.OK);
    }

    @PostMapping("/add/product/bid")
    public ResponseEntity<AddBidResponse> addBidToProduct(@RequestBody AddBidRequest addBidRequest,
                                                     @RequestHeader(name = "AUTHORIZATION", required = true) String authorization) {
        return new ResponseEntity<>(productService.addProductToBid(addBidRequest, authorization), HttpStatus.OK);
    }
}
