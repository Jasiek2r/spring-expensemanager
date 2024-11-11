package com.janek.app.CommandLineRunners.AppCommandLineRunner;

import com.janek.app.Entities.*;
import com.janek.app.Services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.Scanner;

/*
    This is AppCommandLineRunner's command handler
    which executes the logic behind them
 */

@Component
public class AppCommandLineDispatch {


    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    public AppCommandLineDispatch(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    private Scanner standardInputScanner = new Scanner(System.in);

    public boolean dispatch(String command){
        switch (command) {
            case "list categories":
                listCategories();
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


}
