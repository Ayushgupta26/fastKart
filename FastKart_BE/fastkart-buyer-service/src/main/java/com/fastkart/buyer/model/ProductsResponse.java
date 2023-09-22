package com.fastkart.buyer.model;

import com.fastkart.buyer.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsResponse {

    private List<Product> products;
    private Integer currentPage;
    private Long totalItems;
    private Integer totalPages;
}
