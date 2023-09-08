package com.fastkart.seller.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="product_bid")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBid {


    @Column(name = "buyer_user_name")
    private String buyerUserName;
    @Column(name = "bid_amount")
    private int bidAmount;

    @Id
    @ManyToOne
    @JoinColumn(name="product_id", nullable = false)
    private Product product;
}
