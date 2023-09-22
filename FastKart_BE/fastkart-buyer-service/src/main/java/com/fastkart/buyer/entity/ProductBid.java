package com.fastkart.buyer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="product_bid")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBid {

    @Id
    @GeneratedValue(generator = "product_bid_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="product_bid_seq",sequenceName="product_bid_seq", allocationSize=1)
    @Column(name = "bid_id")
    private int bidId;

    @Column(name = "buyer_user_name")
    private String buyerUserName;

    @Column(name = "bid_amount")
    private int bidAmount;

    @Column(name = "bid_date_time")
    private LocalDateTime bidDateTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;
}
