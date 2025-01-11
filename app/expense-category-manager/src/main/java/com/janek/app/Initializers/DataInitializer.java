package com.janek.app.Initializers;

import com.janek.app.Events.InitializationEvent;
import com.janek.app.Services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/*
    This class is responsible for pre-populating app data with some dummy examples
 */
@Component
@Order(1)
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private ExpenseCategoryService categoryService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    private final RestTemplate restTemplate;

    @Value("${expense.manager.name}")
    private String expenseManagerName;

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
    private void sendInitializationEvent() {
        InitializationEvent initializationEvent = new InitializationEvent();

        String expenseManagementUri = "";

        // Send POST request to the elements management application
        while(true){
            try{
                expenseManagementUri = loadBalancerClient
                        .choose(expenseManagerName)
                        .getUri().toString();

            }catch(Exception e){
                System.out.println("Initialization failed, retrying");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                continue;
            }
            break;
        }


        String url = expenseManagementUri + "/api/expense-manager/events/handle-initialization-event";
        System.out.println("Sending to " +url);
        restTemplate.postForEntity(url, initializationEvent, Void.class);
        System.out.println("Sent initialization event to " + url);
    }
}
