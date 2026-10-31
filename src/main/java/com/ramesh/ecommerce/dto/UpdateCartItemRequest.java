package com.ramesh.ecommerce.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class UpdateCartItemRequest {

    @NotBlank(message = "quantity must be required")
    @Positive(message = "quantity must be positive")
    private Integer quantity;
}
