package com.janek.app.CommandLineRunners.AppCommandLineRunner;

import com.janek.app.Entities.*;
import com.janek.app.Services.ExpenseCategoryService;
import com.janek.app.Services.ExpenseService;
import com.janek.app.Utils.DataValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

/*
    This is AppCommandLineRunner's command handler
    which executes the logic behind them
 */

@Component
public class AppCommandLineDispatch {


    private ExpenseService expenseService;
    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    public AppCommandLineDispatch(ExpenseService expenseService, ExpenseCategoryService expenseCategoryService) {
        this.expenseService = expenseService;
        this.expenseCategoryService = expenseCategoryService;
    }

    private Scanner standardInputScanner = new Scanner(System.in);

    public boolean dispatch(String command){
        switch (command) {
            case "list categories":
                listCategories();
                break;
            case "list expenses":
                listExpenses();
                break;
            case "add expense":
                addExpense();
                break;
            case "delete expense":
                deleteExpense();
                break;
            case "exit":
                System.out.println("Exiting application.");
                return false;
            default:
                System.out.println("Unknown command. Please try again.");
                break;
        }
        return true;
    }



    private void listCategories() {
        System.out.println("\n--- Categories ---");
        List<ExpenseCategory> categories = expenseCategoryService.findAllCategories();
        if (categories.isEmpty()) {
            System.out.println("No categories found.");
        } else {
            categories.forEach(category -> System.out.println(category.toString()));
        }
    }

    private void listExpenses() {
        System.out.println("\n--- Expenses ---");
        List<Expense> expenses = expenseService.findAllExpenses();
        if (expenses.isEmpty()) {
            System.out.println("No expenses found.");
        } else {
            expenses.forEach(expense -> System.out.println(expense.toString()));
        }
    }

    private void addExpense() {

        System.out.println("\nEnter the details for the new expense:");

        // Enter expense name
        System.out.print("Name: ");
        String name = standardInputScanner.nextLine();

        // Enter expense description
        System.out.print("Description: ");
        String description = standardInputScanner.nextLine();

        // Enter expense amount
        System.out.print("Amount: ");
        String unsafeStringAmount = standardInputScanner.nextLine();
        if(!DataValidator.isValidPositiveNumber(unsafeStringAmount)){
            System.out.println("Invalid Amount - please enter a positive number");
            return;
        }

        double amount = Double.parseDouble(unsafeStringAmount);


        // Select category
        listCategories();
        System.out.println("Select a category ID:");
        UUID categoryId;
        try{
            categoryId = UUID.fromString(standardInputScanner.nextLine());
        }catch(Exception e){
            System.out.println("Invalid category id");
            return;
        }


        // Find the category by ID
        Optional<ExpenseCategory> queryResult = expenseCategoryService.findCategoryById(categoryId);
        if (queryResult.isEmpty()) {
            System.out.println("Category not found. Expense not added.");
            return;
        }
        ExpenseCategory category = queryResult.get();


        // Create and save the expense
        Expense newExpense = Expense.builder()
                .id(UUID.randomUUID())
                .name(name)
                .description(description)
                .amount(amount)
                .category(category)
                .build();
        expenseService.saveExpense(newExpense);
        System.out.println("Expense added successfully.");
    }

    private void deleteExpense() {
        System.out.print("\nEnter the ID of the expense to delete: ");
        UUID expenseId;
        try{
            expenseId = UUID.fromString(standardInputScanner.nextLine());
        }catch(Exception e){
            System.out.println("Invalid expense id");
            return;
        }

        Optional<Expense> queryResult = expenseService.findExpenseById(expenseId);
        if (queryResult.isPresent()) {
            expenseService.deleteExpense(expenseId);
            System.out.println("Expense deleted successfully.");
        } else {
            System.out.println("Expense with ID " + expenseId + " not found.");
        }
    }
}
