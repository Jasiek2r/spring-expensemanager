package com.janek.app.Utils;

import java.util.*;
import com.janek.app.Entities.*;


public class DataGenerator{
    public static List<ExpenseCategory> createDummyData(){
        List<ExpenseCategory> dummyExpenseCategories = new ArrayList<ExpenseCategory>();
        
        //create categories        
        ExpenseCategory groceryExpenses = ExpenseCategory.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .description("Basic food expenses")
                .budget(100)
                .expenses(new ArrayList<>())
                .build();

        ExpenseCategory housingExpenses = ExpenseCategory.builder()
                .id(UUID.randomUUID())
                .name("Housing")
                .description("Rent and utilities")
                .budget(200)
                .expenses(new ArrayList<>())
                .build();

        ExpenseCategory entertainmentExpenses = ExpenseCategory.builder()
                .id(UUID.randomUUID())
                .name("Entertainment")
                .description("Movies and games")
                .budget(300)
                .expenses(new ArrayList<>())
                .build();

        // Populate the data for each category
        groceryExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Dinner")
                .description("Family dinner at a restaurant")
                .amount(10)
                .category(groceryExpenses)
                .build());

        groceryExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Groceries")
                .description("Grocery shopping")
                .amount(20)
                .category(groceryExpenses)
                .build());

        groceryExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Lunch")
                .description("Lunch at McDonald's")
                .amount(15)
                .category(groceryExpenses)
                .build());

        housingExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Rent")
                .description("Monthly rent")
                .amount(100)
                .category(housingExpenses)
                .build());

        housingExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Utilities")
                .description("Electricity and water")
                .amount(50)
                .category(housingExpenses)
                .build());

        entertainmentExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Games")
                .description("Video games")
                .amount(20)
                .category(entertainmentExpenses)
                .build());

        entertainmentExpenses.addExpense(Expense.builder()
                .id(UUID.randomUUID())
                .name("Movies")
                .description("Movie tickets")
                .amount(10)
                .category(entertainmentExpenses)
                .build());
        
        //add categories to a list
        dummyExpenseCategories.add(groceryExpenses);
        dummyExpenseCategories.add(housingExpenses);
        dummyExpenseCategories.add(entertainmentExpenses);
        return dummyExpenseCategories;
    }
}
