package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.CategoryRequestDTO;
import com.ramesh.ecommerce.model.Category;
import com.ramesh.ecommerce.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Category APIs",description = "Operations Related to Category")
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @Operation(summary = "Create The Category")
    @ApiResponse(responseCode = "201",description = "Category Created")
    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody CategoryRequestDTO dto)
    {
        Category category = service.createCategory(dto);
        return ResponseEntity.status(201).body(category);
    }

    @Operation(summary = "Get All Categories")
    @ApiResponse(responseCode = "200",description = " Returns the List Of Categories")
    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories()
    {
        return ResponseEntity.ok(service.getAllCategories());
    }

    @Operation(summary = "Get Category By Id")
    @ApiResponses({@ApiResponse(responseCode = "200",description = "Category is found"),
            @ApiResponse(responseCode = "404",description = "Category is Not Found")})
    @GetMapping("/{id}")
    public  ResponseEntity<Category> getCategoryById(@PathVariable Long id)
    {
        return ResponseEntity.ok(service.getCategoryById(id));
    }


}
