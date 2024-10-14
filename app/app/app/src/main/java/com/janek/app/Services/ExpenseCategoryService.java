package com.janek.app.Services;

import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Repositories.ExpenseCategoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
    A service which handles all necessary operations regarding Expense Categories on a H2 database
    using Jakarta Persistence API
    Every exposed method makes a call to repository
 */

@Service
@Transactional
public class ExpenseCategoryService {

    @Autowired
    private ExpenseCategoryRepository categoryRepository;


    //Fetch all expense categories in repository
    public List<ExpenseCategory> findAllCategories() {
        return categoryRepository.findAll();
    }

    //Fetch an expense category from repository by its id
    public Optional<ExpenseCategory> findCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    //Save a new expense category to the repository
    public ExpenseCategory saveCategory(ExpenseCategory category) {
        return categoryRepository.save(category);
    }

    //Delete an expense category from repository
    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }
}
