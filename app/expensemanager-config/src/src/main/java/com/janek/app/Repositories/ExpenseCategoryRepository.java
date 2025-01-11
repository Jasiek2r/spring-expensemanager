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

/*
    An interface of ExpenseCategory repository created using Jakarta Persistence API
 */

@Repository
public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategory, UUID>{
    //Fetch an expense category by its id
    Optional<ExpenseCategory> findById(UUID expenseCategoryId);
    //Fetch all expense categories in repository
    List<ExpenseCategory> findAll();
    //Save a new expense category to the repository
    ExpenseCategory save(ExpenseCategory newExpenseCategoryEntity);
    //Delete an expense category
    void delete(ExpenseCategory expenseCategoryEntity);
}
