package com.fastkart.buyer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddBidRequest {

    private int productId;
    private int bidAmount;
}
