package com.janek.app.Initializers;

import com.janek.app.Entities.Expense;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Events.CategoryEvent;
import com.janek.app.Events.InitializationEvent;
import com.janek.app.Services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.discovery.DiscoveryClient;
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

    @Autowired
    private DiscoveryClient discoveryClient;

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
        String expenseManagementUri = discoveryClient
                .getInstances("expense-manager")
                .stream()
                .findFirst()
                .orElseThrow()
                .getUri()
                .toString();
        String url = expenseManagementUri + "/api/expense-manager/events/handle-initialization-event";
        System.out.println("Sending to " +url);
        restTemplate.postForEntity(url, initializationEvent, Void.class);
        System.out.println("Sent initialization event to " + url);
    }
}
