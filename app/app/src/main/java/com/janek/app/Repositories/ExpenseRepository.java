package com.janek.app.Repositories;

import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.List;
import java.util.UUID;

/*
    An interface of Expense repository created using Jakarta Persistence API
 */

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID>{
    //Fetch an expense by id
    Optional<Expense> findById(UUID expenseEntityId);
    //Fetch all expenses in repository
    List<Expense> findAll();
    //Save a new expense to the repository
    Expense save(Expense newExpenseEntity);
    //Query expenses by category
    List<Expense> findByCategory(ExpenseCategory category);
    //Delete an expense
    void delete(Expense expenseEntity);
}
