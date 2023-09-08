package com.fastkart.seller.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="products")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(generator = "product_id_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name="product_id_seq",sequenceName="product_id_seq", allocationSize=1)
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_category")
    private String productCategory;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "min_bid")
    private int minBid;

    @Column(name = "seller_name")
    private String sellerName;

    @OneToMany(mappedBy = "product")
    private List<ProductBid> productBids = new ArrayList<>();
}
