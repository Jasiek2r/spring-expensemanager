package com.janek.app.Controllers;

import com.janek.app.Entities.DTO.ExpenseCategoryReadDto;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Events.CategoryEvent;
import com.janek.app.Events.InitializationEvent;
import com.janek.app.Services.ExpenseCategoryService;
import com.janek.app.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/expense-manager/events")
public class EventController {
    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    private final RestTemplate restTemplate;

    public EventController(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @PostMapping("/handle-category-event")
    public ResponseEntity<Void> handleCategoryEvent(@RequestBody CategoryEvent event) {
        String eventAction = event.getAction();
        UUID categoryId = event.getExpenseCategoryId();
        switch(eventAction){
            case "ADD":
                ExpenseCategory newExpenseCategory = ExpenseCategory.builder()
                        .id(categoryId)
                        .build();
                break;
            case "REMOVE":
                expenseCategoryService.deleteCategory(categoryId);
                break;
        }

        return ResponseEntity.ok().build();
    }

    @PostMapping("/handle-initialization-event")
    public ResponseEntity<Void> handleInitializationEvent(@RequestBody InitializationEvent event){
        System.out.println("initialization event hit");
        // fetch categories
        List<ExpenseCategory> expenseCategoryList = expenseCategoryService.findAllCategories();
        List<ExpenseCategoryReadDto> expenseCategoryListDto = expenseCategoryList.stream().map(
                element -> ExpenseCategoryReadDto.builder()
                        .id(element.getId())
                        .name(element.getName())
                        .description(element.getDescription())
                        .budget(element.getBudget())
                        .build()
        ).collect(Collectors.toList());
        event.setExpenseCategories(expenseCategoryListDto);
        // send them back
        sendInitializationEvent(expenseCategoryListDto);
        return ResponseEntity.ok().build();
    }
    private void sendInitializationEvent(List<ExpenseCategoryReadDto> expenseCategoryList){
        InitializationEvent initializationEvent = InitializationEvent.builder()
                .expenseCategories(expenseCategoryList)
                .build();
        // send categories back on request
        System.out.println("sending back categories");
        String categoryManagementUri = loadBalancerClient
                .choose("expense-category-manager")
                .getUri().toString();

        System.out.println("sending to " + categoryManagementUri);

        restTemplate.postForEntity(categoryManagementUri + "/api/expense-manager/events/handle-initialization-event", initializationEvent, Void.class);
    }
}
