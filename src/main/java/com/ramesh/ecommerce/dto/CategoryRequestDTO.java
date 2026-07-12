package com.ramesh.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CategoryRequestDTO {

    @NotBlank(message = "name must be required")
    private String name;
}
