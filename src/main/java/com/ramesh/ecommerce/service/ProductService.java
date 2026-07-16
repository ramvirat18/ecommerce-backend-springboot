package com.ramesh.ecommerce.service;

import com.ramesh.ecommerce.dto.ProductRequestDTO;
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





    public Page<Product> getAllProducts(int page, int size,String sortBy, String direction)
    {
        Sort sort = direction.equalsIgnoreCase("desc")?Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page,size,sort);
        return productRepository.findAll(pageable);
    }



    public List<Product> searchProduct(String keyword)
    {
        return productRepository.findByNameContainingIgnoreCase(keyword);
    }


    public Product getProductById(Long id)
    {


         return productRepository.findById(id).
                 orElseThrow(()-> new  ResourceNotFoundException("Product with id "
                         + id +
                         " not found"));
    }

    public Product addProduct(ProductRequestDTO dto)
    {
        Category category = categoryRepository.findById(dto.getCategory_id()).orElseThrow(()-> new ResourceNotFoundException("Category not found"));
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductRequestDTO dto)
    {
        Product ExistingProduct = productRepository.findById(id)
                        .orElseThrow(()->new  ResourceNotFoundException("Product is not present in the db"));



        ExistingProduct.setName(dto.getName());
        ExistingProduct.setPrice(dto.getPrice());

        ExistingProduct.setCategory(categoryRepository.findById(dto.getCategory_id()).orElseThrow(()->new  ResourceNotFoundException(" Product Category is not found")));
        return productRepository.save(ExistingProduct) ;
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

    public List<Product> getProductsBycategory(Long categoryId)
    {

        return productRepository.findByCategoryId(categoryId);
    }

    public List<Product> getProductsInRange(Double minPrice, Double maxPrice)
    {
        return productRepository.findByPriceBetween(minPrice,maxPrice);
    }

    public List<Product> filterProducts(Long categoryId, Double minPrice,Double maxPrice)
    {
        return productRepository.filterProducts(categoryId,minPrice,maxPrice);
    }




}
