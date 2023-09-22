package com.fastkart.buyer.service;

import com.fastkart.buyer.model.AddBidRequest;
import com.fastkart.buyer.model.AddBidResponse;
import com.fastkart.buyer.model.ProductsResponse;

public interface ProductService {

    ProductsResponse getAllProducts(Integer pageNumber, Integer size);

    AddBidResponse addProductToBid(AddBidRequest addBidRequest, String authorization);
}
