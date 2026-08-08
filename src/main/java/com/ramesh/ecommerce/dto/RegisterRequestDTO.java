package com.ramesh.ecommerce.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequestDTO {

    @NotBlank(message = "name is required")
    private String name;
    @Email(message = "Enter a Valid email")
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "Password is required")
    private String password;
}
