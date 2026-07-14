package com.ramesh.ecommerce.repository;

import com.ramesh.ecommerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByNameContainingIgnoreCase(String keyword);
}
