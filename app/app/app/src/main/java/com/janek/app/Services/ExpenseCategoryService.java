package com.janek.app.Services;

import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Repositories.ExpenseCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ExpenseCategoryService {

    @Autowired
    private ExpenseCategoryRepository categoryRepository;

    public List<ExpenseCategory> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<ExpenseCategory> findCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    public ExpenseCategory saveCategory(ExpenseCategory category) {
        return categoryRepository.save(category);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
