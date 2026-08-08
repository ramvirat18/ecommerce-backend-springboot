package com.ramesh.ecommerce.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRequestDTO {

    @Email(message = "Enter Valid email")
    @NotBlank(message = "Email required")
    private String email;

    @NotBlank(message = "password is required")
    private String password;
}
