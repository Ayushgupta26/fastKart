package com.fastkart.seller.config;

import com.fastkart.seller.entity.Product;
import com.fastkart.seller.model.AddProductRequest;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDateTime;

public class ProductProcessor implements ItemProcessor<AddProductRequest, Product> {

    private String sellerName = null;
    public ProductProcessor(String inputFile) {
        this.sellerName = inputFile;
    }

    @Override
    public Product process(AddProductRequest addProductRequest) throws Exception {
        Product product = new Product();
        product.setProductName(addProductRequest.getProductName());
        product.setProductDescription(addProductRequest.getProductDescription());
        product.setProductCategory(addProductRequest.getProductCategory());
        product.setMinBid(addProductRequest.getMinBid());
        product.setListedDateTime(LocalDateTime.now());
        product.setSellerName(this.sellerName);
        return product;
    }
}
