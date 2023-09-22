package com.fastkart.seller.service;

import com.fastkart.seller.model.AddProductRequest;
import com.fastkart.seller.model.AddProductResponse;
import com.fastkart.seller.model.ProductDetailsResponse;
import com.fastkart.seller.model.ProductsResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductsResponse getProducts(String authorization, Integer pageNumber, Integer size);

    ProductDetailsResponse getProductByProductId(int productId);

    AddProductResponse addProduct(AddProductRequest addProductRequest, String authorization);

    AddProductResponse processProductCSV(MultipartFile file, String authorization) throws Exception;
}
