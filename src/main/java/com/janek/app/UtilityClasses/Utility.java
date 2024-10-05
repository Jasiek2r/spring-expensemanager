package com.janek.app;
import com.janek.app.Expense;
import com.janek.app.ExpenseCategory;
import com.janek.app.ExpenseDto;
import com.janek.app.Utility;
import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.lang.*;

public class Utility{
    
    static final String SPACING = "\n\n\n";

    public static void testExpenseComparison(Expense expense, Expense expense2){
        System.out.println(expense.compareByHash(expense, expense2));
        System.out.println(expense.compareByNaturalOrder(expense, expense2));
    }
    public static void testExpenseCategoryComparison(
        ExpenseCategory expenseCategory,
        ExpenseCategory expenseCategory2){
        
        System.out.println(expenseCategory.compareByHash(expenseCategory, expenseCategory2));
        System.out.println(expenseCategory.compareByNaturalOrder(expenseCategory, expenseCategory2));
    }

    public static void testToString(Expense expense, ExpenseCategory expenseCategory){
        // test if the toString method works
        System.out.println(expense.toString());
        System.out.println(expenseCategory.toString());
    }

    public static void testComparison(
        Expense  expense,
        Expense expense2,
        ExpenseCategory expenseCategory,
        ExpenseCategory expenseCategory2){

        testExpenseComparison(expense, expense2);
        testExpenseCategoryComparison(expenseCategory, expenseCategory2);
    }

    public static void testDto(Expense expense){
        ExpenseDto expenseDto = ExpenseDto.builder()
                .name(expense.getName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .categoryName(expense.getCategory().getName())
                .build();
        System.out.println(expenseDto);
    }
    public static void printSpace(){
        System.out.println(SPACING);
    }
}