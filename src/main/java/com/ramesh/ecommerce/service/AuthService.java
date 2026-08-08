package com.ramesh.ecommerce.service;


import com.ramesh.ecommerce.dto.RegisterRequestDTO;
import com.ramesh.ecommerce.dto.RegisterResponseDTO;
import com.ramesh.ecommerce.model.Role;
import com.ramesh.ecommerce.model.User;
import com.ramesh.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponseDTO register(RegisterRequestDTO dto)
    {
        if(userRepository.findByEmail(dto.getEmail()).isPresent())
        {
            throw new RuntimeException("Email is already present");
        }

        User user = User.builder().
                 name(dto.getName()).
                email(dto.getEmail()).
                password(passwordEncoder.encode(dto.getPassword())).
                role(Role.USER).
                build();

        User savedUser = userRepository.save(user);

        return RegisterResponseDTO.builder()
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .role(savedUser.getRole())
                .build();
    }

}
