package com.janek.app.CommandLineRunners.AppCommandLineRunner;


import com.janek.app.Services.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Scanner;

/*
    This class is responsible for continuously retrieving user input
    through the STDIN console and dispatching commands
 */

@Component
@Order(2)
public class AppCommandLineRunner implements CommandLineRunner {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    private Scanner standardInputScanner = new Scanner(System.in);

    @Autowired
    private AppCommandLineDispatch commandLineDispatch;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("AppCommandLineRunner started.");
        boolean running = true;

        while (running) {
            displayMenu();
            String command = standardInputScanner.nextLine().trim().toLowerCase();

            /*
                The command is being dispatched to the handling class
                A status flag "running" is being returned from the call
             */
            running = commandLineDispatch.dispatch(command);
        }
    }
    private void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. list categories - List all expense categories");
        System.out.println("2. list expenses - List all expenses");
        System.out.println("3. add expense - Add a new expense");
        System.out.println("4. delete expense - Delete an expense by ID");
        System.out.println("5. exit - Exit the application");
        System.out.print("Enter command: ");
    }

}
