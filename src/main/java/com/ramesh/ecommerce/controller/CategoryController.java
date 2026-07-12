package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.CategoryRequestDTO;
import com.ramesh.ecommerce.model.Category;
import com.ramesh.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequestDTO dto)
    {
        Category category = service.createCategory(dto);
        return ResponseEntity.status(201).body(category);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories()
    {
        return ResponseEntity.ok(service.getAllCategories());
    }


}
