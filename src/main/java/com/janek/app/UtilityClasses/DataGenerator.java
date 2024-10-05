package com.janek.app;
import com.janek.app.Expense;
import com.janek.app.ExpenseCategory;
import com.janek.app.ExpenseDto;
import com.janek.app.Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.Set;

public class DataGenerator{
    public static List<ExpenseCategory> createDummyData(){
        List<ExpenseCategory> dummyExpenseCategories = new ArrayList<ExpenseCategory>();
        
        //create categories        
        ExpenseCategory groceryExpenses = ExpenseCategory.builder()
        .name("Groceries")
        .description("Basic food expenses")
        .budget(100)
        .expenses(new ArrayList<>())
        .build();

        ExpenseCategory housingExpenses = ExpenseCategory.builder()
                .name("Housing")
                .description("Rent and utilities")
                .budget(200)
                .expenses(new ArrayList<>())
                .build();

        ExpenseCategory entertainmentExpenses = ExpenseCategory.builder()
                .name("Entertainment")
                .description("Movies and games")
                .budget(300)
                .expenses(new ArrayList<>())
                .build();

        // Populate the data for each category
        groceryExpenses.addExpense(Expense.builder()
                .name("Dinner")
                .description("Family dinner at a restaurant")
                .amount(10)
                .category(groceryExpenses)
                .build());

        groceryExpenses.addExpense(Expense.builder()
                .name("Groceries")
                .description("Grocery shopping")
                .amount(20)
                .category(groceryExpenses)
                .build());

        groceryExpenses.addExpense(Expense.builder()
                .name("Lunch")
                .description("Lunch at McDonald's")
                .amount(15)
                .category(groceryExpenses)
                .build());

        housingExpenses.addExpense(Expense.builder()
                .name("Rent")
                .description("Monthly rent")
                .amount(100)
                .category(housingExpenses)
                .build());

        housingExpenses.addExpense(Expense.builder()
                .name("Utilities")
                .description("Electricity and water")
                .amount(50)
                .category(housingExpenses)
                .build());

        entertainmentExpenses.addExpense(Expense.builder()
                .name("Games")
                .description("Video games")
                .amount(20)
                .category(entertainmentExpenses)
                .build());

        entertainmentExpenses.addExpense(Expense.builder()
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
