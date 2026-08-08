package com.ramesh.ecommerce.dto;

import com.ramesh.ecommerce.model.Role;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterResponseDTO {

    private String name;
    private String email;
    private Role role;
}
