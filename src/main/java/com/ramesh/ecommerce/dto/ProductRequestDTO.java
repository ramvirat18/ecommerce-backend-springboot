package com.ramesh.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String name;

    @NotBlank(message = "price is required")
    @Positive(message = "Price must be greater than zero")
    private double price;

    @NotBlank(message = "Category id is required")
    private Long category_id;



}
