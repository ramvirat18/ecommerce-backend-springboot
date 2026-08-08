package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.LoginRequestDTO;
import com.ramesh.ecommerce.dto.LoginResponseDTO;
import com.ramesh.ecommerce.dto.RegisterRequestDTO;
import com.ramesh.ecommerce.dto.RegisterResponseDTO;
import com.ramesh.ecommerce.model.User;
import com.ramesh.ecommerce.service.AuthService;
import com.ramesh.ecommerce.service.JwtService;
import io.jsonwebtoken.security.Keys;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.crypto.SecretKey;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final AuthService authService;


    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO dto)
    {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.getEmail(),dto.getPassword()));

        String token = jwtService.generateToken(dto.getEmail());
        return new LoginResponseDTO(token);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto)
    {
        RegisterResponseDTO user = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }





}
