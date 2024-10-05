package com.janek.app;
import com.janek.app.Expense;
import com.janek.app.ExpenseCategory;
import com.janek.app.Utility;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.concurrent.*;
import java.io.*;
import java.lang.*;

public class Pipeline{
    
    public static void runExpenseCategoryPool(
        List<ExpenseCategory> dummyExpenseCategories,
        int numberOfThreads, int delayMiliseconds){
        // create a custom thread pool with a specified number of threads
        ForkJoinPool customPool = new ForkJoinPool(numberOfThreads);

        System.out.println("EXECUTING PIPELINE WITH  " + numberOfThreads + " THREADS");


        /* 
           submitting the task to the pool to print out expenses
           in categories in parallel 
        */
        customPool.submit(() -> dummyExpenseCategories.parallelStream()
            .forEach(category -> {
                System.out.println("Processing category: " + category.getName());
                category.getExpenses()
                    .forEach(expense -> {
                        System.out.println(" " + expense.toString());
                        // simulating some workload
                        try {
                            Thread.sleep(delayMiliseconds); 
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    });
            })
        );

        

        // close the custom thread pool
        customPool.shutdown();

        //wait for tasks to complete
        try {
            if (!customPool.awaitTermination(30000, TimeUnit.MILLISECONDS)) {
                System.out.println("POOL SHUTDOWN");
                customPool.shutdownNow();
            }                   
        } catch (InterruptedException e) {              
            customPool.shutdownNow();
        }
    }
}