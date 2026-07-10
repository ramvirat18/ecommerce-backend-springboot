package com.ramesh.ecommerce.service;

import com.ramesh.ecommerce.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private List<Product> products = new ArrayList<>();

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
        for(Product product: products)
        {
            if(product.getId().equals(id))
            {
                return product;
            }
        }

        return null;
    }

    public Product addProduct(Product product)
    {
        products.add(product);
        return product;
    }

}
