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
                .build();

        ExpenseCategory housingExpenses = ExpenseCategory.builder()
                .id(UUID.randomUUID())
                .name("Housing")
                .description("Rent and utilities")
                .budget(200)
                .build();

        ExpenseCategory entertainmentExpenses = ExpenseCategory.builder()
                .id(UUID.randomUUID())
                .name("Entertainment")
                .description("Movies and games")
                .budget(300)
                .build();
        
        //add categories to a list
        dummyExpenseCategories.add(groceryExpenses);
        dummyExpenseCategories.add(housingExpenses);
        dummyExpenseCategories.add(entertainmentExpenses);
        return dummyExpenseCategories;
    }
}
