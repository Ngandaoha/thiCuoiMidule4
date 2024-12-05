package com.c0324m4.finaltestm4.service;

import com.c0324m4.finaltestm4.model.Category;
import com.c0324m4.finaltestm4.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

  
    public List<Category> getAllCategories() {
        return categoryRepository.findAll(); 
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

}
