package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.ProductRequestDTO;
import com.ramesh.ecommerce.model.Product;
import com.ramesh.ecommerce.service.ProductService;
import jakarta.validation.Valid;
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

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts()
    {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id)
    {

        if( productService.getProductById(id)==null)
        {
           return ResponseEntity.notFound().build();
        }

        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> addProduct(@Valid @RequestBody ProductRequestDTO dto)
    {
        Product product=productService.addProduct(dto);
        return ResponseEntity.status(201).body(product);
        //return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequestDTO dto)
    {

        Product updatedProduct=productService.updateProduct(id,dto);
        if(updatedProduct==null)
        {
            return ResponseEntity.notFound().build();
        }

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


}
