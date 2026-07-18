package com.ramesh.ecommerce.service;

import com.ramesh.ecommerce.dto.ProductRequestDTO;
import com.ramesh.ecommerce.dto.ProductResponseDTO;
import com.ramesh.ecommerce.exception.CategoryNotFoundException;
import com.ramesh.ecommerce.exception.ProductNotFoundException;
import com.ramesh.ecommerce.exception.ResourceNotFoundException;
import com.ramesh.ecommerce.model.Category;
import com.ramesh.ecommerce.model.Product;
import com.ramesh.ecommerce.repository.CategoryRepository;
import com.ramesh.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;





    public Page<ProductResponseDTO> getAllProducts(int page, int size,String sortBy, String direction)
    {
        Sort sort = direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        return productRepository.findAll(pageable)
                .map(this::convertToDTO);

    }



    public List<ProductResponseDTO> searchProduct(String keyword)
    {
        return productRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    public ProductResponseDTO getProductById(Long id)
    {


         Product product = productRepository.findById(id).
                 orElseThrow(()-> new ProductNotFoundException("Product with id "
                         + id +
                         " not found"));

         return convertToDTO(product);
    }

    public ProductResponseDTO addProduct(ProductRequestDTO dto)
    {
        Category category = categoryRepository.findById(dto.getCategory_id()).orElseThrow(()-> new CategoryNotFoundException("Category with id "+dto.getCategory_id()+" is not found"));
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        Product addedProduct = productRepository.save(product);
        return convertToDTO(addedProduct);
    }

    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto)
    {
        Product ExistingProduct = productRepository.findById(id)
                        .orElseThrow(()->new ProductNotFoundException("Product with id " + id + " is not found"));



        ExistingProduct.setName(dto.getName());
        ExistingProduct.setPrice(dto.getPrice());

        ExistingProduct.setCategory(categoryRepository.findById(dto.getCategory_id()).orElseThrow(()->new CategoryNotFoundException("Category with id "+dto.getCategory_id()+" is not found")));
        Product updatedProduct= productRepository.save(ExistingProduct) ;
        return convertToDTO(updatedProduct);
    }

    public boolean deleteProductById(Long id)
    {
        if( !productRepository.existsById(id))
        {
            return false;
        }

        productRepository.deleteById(id);
        return true;
    }

    public List<ProductResponseDTO> getProductsBycategory(Long categoryId)
    {

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProductResponseDTO> getProductsInRange(Double minPrice, Double maxPrice)
    {
        return productRepository.findByPriceBetween(minPrice,maxPrice)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public List<ProductResponseDTO> filterProducts(Long categoryId, Double minPrice,Double maxPrice)
    {
        return productRepository.filterProducts(categoryId,minPrice,maxPrice)
                .stream()
                .map(this::convertToDTO)
                .toList();
    }


    public ProductResponseDTO convertToDTO(Product product)
    {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .categoryName(product.getCategory().getName())
                .build();
    }


}
