package com.fastkart.seller.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotBlank(message = "Minimum Bid is mandatory")
    private int minBid;
}
