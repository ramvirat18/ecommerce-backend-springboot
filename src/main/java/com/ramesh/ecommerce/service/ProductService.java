package com.ramesh.ecommerce.service;

import com.ramesh.ecommerce.dto.ProductRequestDTO;
import com.ramesh.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();
    private Long nextId=3L;

    public ProductService()
    {
        products.add(new Product(1L,"Laptop",50000.00));
        products.add(new Product(2L,"phone",30000.00));
    }

    public List<Product> getAllProducts()
    {
        return products;
    }

    public Product getProductById(Long id)
    {


         return products.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst()
            .orElse(null);
    }

    public Product addProduct(ProductRequestDTO dto)
    {

        Product product = new Product(nextId++,dto.getName(),dto.getPrice());
        products.add(product);
        return product;
    }

    public Product updateProduct(Long id, ProductRequestDTO dto)
    {
        Product product = getProductById(id);
        if(product==null)
        {
            return null;
        }

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        return product;
    }

    public boolean deleteProductById(Long id)
    {
        return products.removeIf(
                p->p.getId().equals(id)
        );
    }


}
