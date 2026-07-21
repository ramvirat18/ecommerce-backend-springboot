package com.ramesh.ecommerce.controller;

import com.ramesh.ecommerce.dto.ProductRequestDTO;
import com.ramesh.ecommerce.dto.ProductResponseDTO;
import com.ramesh.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product APIs",
description = "Operations related to products")
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService)
    {
        this.productService=productService;
    }

    @Operation(summary = "Get all Products",
    description = "Returns paginated List of Products")
    @ApiResponse(responseCode ="200",description = "returns the paginated list of products")
    @GetMapping()
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(@RequestParam int page, @RequestParam int size,@RequestParam String sortBy,@RequestParam String direction)
    {
        return ResponseEntity.ok(productService.getAllProducts(page,size,sortBy,direction));
    }


@Operation(summary = "Search Products",
description = "search the product by keyword")
@ApiResponses({@ApiResponse(responseCode = "200",description = "Return The List of Products"),
@ApiResponse(responseCode = "404",description = "products are not found for the keyword")})
    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>> searchProduct(@RequestParam String keyword)
    {
        return ResponseEntity.ok(productService.searchProduct(keyword));
    }



    @Operation(summary = "get product by id",description = "returns the product related to the id")
    @ApiResponses({@ApiResponse(responseCode = "200",description = "Product found"),
    @ApiResponse(responseCode = "404",description = "Not Found")})
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id)
    {
        return  ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "create Product")
    @ApiResponse(responseCode = "201",description = "product is created successfully")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> addProduct(@Valid @RequestBody ProductRequestDTO dto)
    {
        ProductResponseDTO responseDTO=productService.addProduct(dto);
        return ResponseEntity.status(201).body(responseDTO);
        //return productService.addProduct(product);
    }

    @Operation(summary = "Update Product")
    @ApiResponses({@ApiResponse(responseCode = "200",description = "Update the Product"),
    @ApiResponse(responseCode = "404",description = "The Product is Not Found For Required Id")})
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductRequestDTO dto)
    {

        ProductResponseDTO updatedProduct=productService.updateProduct(id,dto);


        return ResponseEntity.ok(updatedProduct);

    }

    @Operation(summary = "Delete Product",description = "Delete the product by id")
    @ApiResponses({@ApiResponse(responseCode = "204",description = "Product Deleted No Content"),
    @ApiResponse(responseCode = "404",description = "Product Not Found for Deleting")})
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

    @Operation(summary = "Gets the Products By Category Id")
    @ApiResponses({@ApiResponse(responseCode = "200",description = "Returns the List of products"),
    @ApiResponse(responseCode = "404",description = "Category Id Is Not Found")})
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>> getProductsByCategory(@PathVariable Long categoryId)
    {
        return ResponseEntity.ok(productService.getProductsBycategory(categoryId));
    }

    @Operation(summary = "Get The Products WithIn Price Range ")
    @ApiResponse(responseCode = "200",description = "Returns The List of Products Within The Given Price Range")
    @GetMapping("/price-range")
    public ResponseEntity<List<ProductResponseDTO>> getProductsInRange(@RequestParam Double minPrice, Double maxPrice)
    {
        return ResponseEntity.ok(productService.getProductsInRange(minPrice,maxPrice));
    }

    @Operation(summary = "Filter Products")
    @ApiResponse(responseCode = "200",description = "It filters The Products By CategoryId and Price Range")
    @GetMapping("/filter")
    public ResponseEntity<List<ProductResponseDTO>> filterProducts(@RequestParam Long categoryId, @RequestParam Double minPrice ,@RequestParam Double maxPrice)
    {
        return ResponseEntity.ok(productService.filterProducts(categoryId,minPrice,maxPrice));
    }



}
