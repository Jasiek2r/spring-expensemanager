package com.janek.app.Initializers;

import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Services.ExpenseCategoryService;
import com.janek.app.Services.ExpenseService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.janek.app.Utils.*;

import java.util.*;
import java.util.stream.Collectors;

/*
    This class is responsible for pre-populating app data with some dummy examples
 */
@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ExpenseCategoryService categoryService;

    @Autowired
    private ExpenseService expenseService;

    public DataInitializer() {
        System.out.println("DataInitializer bean created.");
    }

    @Override
    public void run(String... args){
        try{
            //generate data
            List<ExpenseCategory> expenseCategoryList = DataGenerator.createDummyData();
            //create a list of expenses by flattening the list of categories using Stream API
            List<Expense> expenseList = expenseCategoryList.stream()
                    .flatMap(expenseCategory -> expenseCategory.getExpenses().stream())
                    .collect(Collectors.toList());
            //Save the expense categories into the repository through an autowired service
            for(ExpenseCategory expenseCategory : expenseCategoryList){
                categoryService.saveCategory(expenseCategory);
            }
            categoryService.flush();
            //Save the expenses into the repository through an autowired service
            for(Expense expense : expenseList){
                expenseService.saveExpense(expense);
            }
            expenseService.flush();
        }catch (Exception e) {
            System.err.println("Error during data initialization: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
