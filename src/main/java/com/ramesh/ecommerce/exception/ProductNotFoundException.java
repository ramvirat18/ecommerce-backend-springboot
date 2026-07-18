package com.ramesh.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message)
    {
        super(message);
    }
}
