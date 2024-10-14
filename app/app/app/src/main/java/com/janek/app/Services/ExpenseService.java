package com.janek.app.Services;
import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Repositories.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/*
    A service which handles all necessary operations regarding Expenses on a H2 database
    using Jakarta Persistence API
    Every exposed method makes a call to repository
 */
@Service
@Transactional
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    //Fetch all expenses from repository
    public List<Expense> findAllExpenses() {
        return expenseRepository.findAll();
    }

    //Fetch an expense by id from repository
    public Optional<Expense> findExpenseById(UUID id) {
        return expenseRepository.findById(id);
    }

    //Save an expense to the repository
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    //Delete an expense by its id
    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }

    //Fetch a list of expenses that belong to a given category
    public List<Expense> findExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findByCategory(category);
    }
}
