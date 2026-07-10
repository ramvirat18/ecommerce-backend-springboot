package com.ramesh.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public class ProductRequestDTO {

    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @Positive(message = "Price must be greater than zero")
    private double price;

    public ProductRequestDTO(String name,double price)
    {
        this.name=name;
        this.price=price;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name=name;
    }

    public double getPrice()
    {
        return price;
    }

    public void setPrice(double price)
    {
        this.price=price;
    }
}
