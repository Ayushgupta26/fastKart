package com.fastkart.seller.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name="product_bid")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductBid {

    @Id
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
