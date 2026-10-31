package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.AddToCartRequest;
import com.ramesh.ecommerce.dto.CartResponse;
import com.ramesh.ecommerce.dto.UpdateCartItemRequest;
import com.ramesh.ecommerce.service.CartService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartControler {

    private final CartService cartService;


    // 1. View cart
    @GetMapping
    public ResponseEntity<CartResponse> getCart(
            Authentication authentication
    ) {

        String email = authentication.getName();

        CartResponse response = cartService.getCart(email);

        return ResponseEntity.ok(response);
    }

    // 2. Add product to cart
    @PostMapping("/items")
    public ResponseEntity<CartResponse> addItem(
            Authentication authentication,
            @Valid @RequestBody AddToCartRequest request
    ) {

        String email = authentication.getName();

        CartResponse response = cartService.addItem(email, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // 3. Update product quantity
    @PutMapping("/items/{productId}")
    public ResponseEntity<CartResponse> updateQuantity(
            Authentication authentication,
            @PathVariable Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ) {

        String email = authentication.getName();

        CartResponse response = cartService.updateQuantity(
                email,
                productId,
                request
        );

        return ResponseEntity.ok(response);
    }

    // 4. Remove product from cart
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<CartResponse> removeItem(
            Authentication authentication,
            @PathVariable Long productId
    ) {

        String email = authentication.getName();

        CartResponse response = cartService.removeItem(
                email,
                productId
        );

        return ResponseEntity.ok(response);
    }

    // 5. Clear entire cart
    @DeleteMapping
    public ResponseEntity<CartResponse> clearCart(
            Authentication authentication
    ) {

        String email = authentication.getName();

        CartResponse response = cartService.clearCart(email);

        return ResponseEntity.ok(response);
    }
}