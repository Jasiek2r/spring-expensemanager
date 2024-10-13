package com.janek.app.Services;
import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Repositories.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;

    public List<Expense> findAllExpenses() {
        return expenseRepository.findAll();
    }

    public Optional<Expense> findExpenseById(UUID id) {
        return expenseRepository.findById(id);
    }

    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(UUID id) {
        expenseRepository.deleteById(id);
    }

    public List<Expense> findExpensesByCategory(ExpenseCategory category) {
        return expenseRepository.findByCategory(category);
    }
}
