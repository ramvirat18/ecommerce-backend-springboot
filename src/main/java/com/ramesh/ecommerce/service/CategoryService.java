package com.ramesh.ecommerce.service;

import com.ramesh.ecommerce.dto.CategoryRequestDTO;
import com.ramesh.ecommerce.model.Category;
import com.ramesh.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;

    public Category createCategory(CategoryRequestDTO dto)
    {
        Category category = new Category();
        category.setName(dto.getName());

        return repository.save(category);
    }

    public List<Category> getAllCategories()
    {
        return repository.findAll();
    }

}
