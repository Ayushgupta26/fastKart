package com.fastkart.buyer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/buyer")
public class ProductController {

    @GetMapping("/products")
    public List<String> getProducts(){
        return new ArrayList<>();
    }
}
