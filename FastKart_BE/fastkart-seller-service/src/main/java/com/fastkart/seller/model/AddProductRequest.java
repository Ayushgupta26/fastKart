package com.fastkart.seller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddProductRequest {

    @NotBlank(message = "Product Name is mandatory")
    private String productName;

    @NotBlank(message = "Product Category is mandatory")
    private String productCategory;

    @NotBlank(message = "Product Description is mandatory")
    private String productDescription;

    @Min(10)
    private int minBid;
}
