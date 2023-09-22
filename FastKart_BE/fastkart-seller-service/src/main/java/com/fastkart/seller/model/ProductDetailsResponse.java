package com.fastkart.seller.model;

import com.fastkart.seller.entity.ProductBid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsResponse {

    private int productId;
    private String productName;
    private String productCategory;
    private String productDescription;
    private int minBid;
    private String sellerName;
    private LocalDateTime listedDateTime;
    private int minBidByBuyer;
    private int maxBidByBuyer;
    private List<ProductBid> productBids = new ArrayList<>();
}
