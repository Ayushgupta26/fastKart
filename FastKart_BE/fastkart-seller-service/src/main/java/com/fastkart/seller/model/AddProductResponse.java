package com.fastkart.seller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductResponse {

    private String responseMessage;
    private int productId;
}
