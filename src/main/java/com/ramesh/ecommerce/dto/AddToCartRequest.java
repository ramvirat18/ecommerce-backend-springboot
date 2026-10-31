package com.ramesh.ecommerce.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AddToCartRequest {

    @NotNull(message = "ProductId is required")
    @Positive(message = " Product Id must be positive")
    private Long productId;

    @NotNull(message = "quantity is required")
    @Positive(message = " quantity is positive")
    private Integer quantity;
}
