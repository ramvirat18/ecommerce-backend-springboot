package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.ProductRequestDTO;
import com.ramesh.ecommerce.dto.ProductResponseDTO;
import com.ramesh.ecommerce.model.Product;
import com.ramesh.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }

    @GetMapping()
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@RequestParam int page, @RequestParam int size,@RequestParam String sortBy,@RequestParam String direction)
    {
        return ResponseEntity.ok(productService.getAllProducts(page,size,sortBy,direction));
    }



    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(@RequestParam String keyword)
    {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }



    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id)
    {
        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO dto)
    {
        ProductResponseDTO responseDTO=productService.addProduct(dto);
        return ResponseEntity.status(201).body(responseDTO);
        //return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequestDTO dto)
    {

        ProductResponseDTO updatedProduct=productService.updateProduct(id,dto);


        return ResponseEntity.ok(updatedProduct);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id)
    {
        boolean ans = productService.deleteProductById(id);
        if(!ans)
        {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long categoryId)
    {
        return ResponseEntity.ok(productService.getProductsBycategory(categoryId));
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<ProductResponseDTO>> getProductsInRange(@RequestParam Double minPrice, Double maxPrice)
    {
        return ResponseEntity.ok(productService.getProductsInRange(minPrice,maxPrice));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(@RequestParam Long categoryId, @RequestParam Double minPrice ,@RequestParam Double maxPrice)
    {
        return ResponseEntity.ok(productService.filterProducts(categoryId,minPrice,maxPrice));
    }



}
