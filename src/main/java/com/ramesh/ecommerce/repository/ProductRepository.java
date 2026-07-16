package com.ramesh.ecommerce.repository;

import com.ramesh.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByPriceBetween(Double minPrice,Double maxPrice);

   @Query("""
    select p
     from Product p 
    where p.category.id=:categoryId
    AND p.price BETWEEN :minPrice AND :maxPrice
"""
   )
    List<Product> filterProducts(@Param("categoryId")  Long categoryId,
                                 @Param("minPrice") Double minPrice,
                                 @Param("maxPrice") Double maxPrice);
}
