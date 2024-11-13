package com.janek.app.Initializers;

import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Events.CategoryEvent;
import com.janek.app.Events.InitializationEvent;
import com.janek.app.Services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import com.janek.app.Utils.*;
import org.springframework.web.client.RestTemplate;

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
    private final String expenseManagementUrl = "http://localhost:8080/api/expense-manager/events"; // Target application URL
    private final RestTemplate restTemplate;

    public DataInitializer(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        System.out.println("DataInitializer bean created.");
    }

    @Override
    public void run(String... args){
        try{
            //download data
            sendInitializationEvent();

        }catch (Exception e) {
            System.err.println("Error during data initialization: " + e.getMessage());
            e.printStackTrace();
        }

    }
    private void sendInitializationEvent(){
        InitializationEvent initializationEvent = new InitializationEvent();
        // Send POST request to the elements management application
        restTemplate.postForEntity(expenseManagementUrl + "/handle-initialization-event", initializationEvent, Void.class);
    }
}
