package com.janek.app;
import com.janek.app.Expense;
import com.janek.app.ExpenseCategory;
import com.janek.app.DataGenerator;
import com.janek.app.ExpenseDto;
import com.janek.app.Utility;
import com.janek.app.FileSerializer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.Set;
import java.util.concurrent.*;
import java.io.*;
import java.lang.*;

public class App 
{

    public static void testExercise1(){
        Expense expense = 
            Expense.builder()
            .name("Test")
            .description("Test")
            .amount(100.0)
            .category(
                ExpenseCategory.builder()
                .name("Test")
                .description("Test")
                .budget(100)
                .expenses(new ArrayList<Expense>())
                .build()
            )
            .build();
        Expense expense2 = 
            Expense.builder()
            .name("Test")
            .description("Test")
            .amount(100.0)
            .category(
                ExpenseCategory.builder()
                .name("Test")
                .description("Test")
                .budget(100)
                .expenses(new ArrayList<Expense>())
                .build()
            )
            .build();
        ExpenseCategory expenseCategory = ExpenseCategory.builder()
        .name("Test").description("Test").budget(100)
        .expenses(new ArrayList<Expense>()).build();
        ExpenseCategory expenseCategory2 = ExpenseCategory.builder()
        .name("Test2").description("Test2").budget(200)
        .expenses(new ArrayList<Expense>()).build();
        
        // test if the compareByHash and compareByNaturalOrder methods work
        Utility.testComparison(expense, expense2,  expenseCategory, expenseCategory2);

        //test if the toString method works on both expense and expense category obj
        Utility.testToString(expense, expenseCategory);

        //test if creating DTO object using builder pattern works fine
        Utility.testDto(expense);
    }
    

    public static void exercise1(){
        System.out.println("Exercise 1");
        
        //print out results to see if everything works fine for exercise 1
        testExercise1();
    }

    public static List<ExpenseCategory> exercise2(){
        System.out.println("Exercise 2");
        //set up dummy data for exercise 2 (see: DataGenerator.java for implementation)
        List<ExpenseCategory> dummyExpenseCategories = DataGenerator.createDummyData();

        dummyExpenseCategories.forEach(category ->
        {
            System.out.println(category.toString());
            category.getExpenses().forEach(expenseItem -> System.out.println(" " +expenseItem.toString()));
        });

        return dummyExpenseCategories;
    }
    public static Set<Expense> exercise3(List<ExpenseCategory> dummyExpenseCategories){
        System.out.println("Exercise 3");
        Set<Expense> allExpenses = dummyExpenseCategories.stream()
            .flatMap(expenseCategory -> expenseCategory.getExpenses().stream())
            .collect(Collectors.toSet());

        allExpenses.stream().forEach(expense -> System.out.println(expense));

        return allExpenses;

    }

    public static void exercise4(Set<Expense> allExpenses){
        System.out.println("Exercise 4");

        allExpenses.stream()
            //we are filtering only the expenses that are higher than 20
            .filter(expense -> expense.getAmount() > 20)
            //then we sort it by name alphabetically
            .sorted((expense1, expense2)->(
                    expense1.getName().compareTo(expense2.getName())
                )
            )
            .forEach(expense -> System.out.println(expense));
    }

    public static void exercise5(Set<Expense> allExpenses){
        System.out.println("Exercise 5");
        List<ExpenseDto> allExpensesList = allExpenses.stream()
        //creating expenseDto objects using a built-in builder
        .map(expense ->
            ExpenseDto.builder()
                .name(expense.getName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .categoryName(expense.getCategory().getName())
                .build()            
        )
        //sorting those expenses in natural order
        .sorted((expenseDto1, expenseDto2) -> 
            expenseDto1.compareByNaturalOrder(expenseDto1,expenseDto2))
        .collect(Collectors.toList());

        //creating another stream to print out those elements
        allExpensesList.stream()
            .forEach(expense -> System.out.println(expense));
    }

    public static void exercise6(List<ExpenseCategory> dummyExpenseCategories){
        System.out.println("Exercise 6");
        // FileSerializer is my own generic class that has methods to serialize 
        // objects to a file and de-serializing it from a file
        FileSerializer<List<ExpenseCategory>> serializer = 
            new FileSerializer<List<ExpenseCategory>>("data.bin");
        // we are using it to serialize the expense categories to a file
        serializer.write(dummyExpenseCategories);
        // and then to retrieve and print it
        List<ExpenseCategory> loadedCategories = serializer.read();
        loadedCategories.forEach(category -> System.out.println(category));
    }

    public static void exercise7(List<ExpenseCategory> dummyExpenseCategories){
        System.out.println("Exercise 7");

        int delayMiliseconds = 3000;
        
        //testing the parallel pipeline for different thread pool sizes
        for(int i = 1; i < 8; i++){
            Pipeline.runExpenseCategoryPool(dummyExpenseCategories, i, delayMiliseconds);
        }
    }
    public static void executeExercises(){
        exercise1();
        Utility.printSpace();
        List<ExpenseCategory>  dummyExpenseCategories = exercise2();
        Utility.printSpace();
        Set<Expense> allExpenses = exercise3(dummyExpenseCategories);
        Utility.printSpace();
        exercise4(allExpenses);
        Utility.printSpace();
        exercise5(allExpenses);
        Utility.printSpace();
        exercise6(dummyExpenseCategories);
        Utility.printSpace();
        exercise7(dummyExpenseCategories);
    }

    public static void main(String[] args) {
        executeExercises();
    }
}
