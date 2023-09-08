package com.fastkart.seller.service;

import com.fastkart.seller.entity.Product;
import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;

import java.util.List;

public interface ProductService {

    List<Product> getProducts(String authorization);

    Product getProductByProductId(int productId);

    AddProductResponse addProduct(AddProductRequest addProductRequest, String authorization);
}
