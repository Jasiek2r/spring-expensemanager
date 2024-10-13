package com.janek.app.Repositories;

import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, UUID>{
    Optional<ExpenseCategory> findById(UUID expenseCategoryId);
    //Fetch all expenses in repository
    List<ExpenseCategory> findAll();
    //Save a new expense to the repository
    ExpenseCategory save(ExpenseCategory newExpenseCategoryEntity);
    //Delete an expense from category
    void delete(ExpenseCategory expenseCategoryEntity);
}
