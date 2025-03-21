package com.janek.app.Controllers;

import com.janek.app.Entities.DTO.ExpenseCategoryUpdateDto;
import com.janek.app.Entities.DTO.ListReadDto.ExpenseCategoriesDto;
import com.janek.app.Entities.DTO.ExpenseCategoryReadDto;
import com.janek.app.Entities.DTO.ExpenseCategoryCreateDto;
import com.janek.app.Entities.DTO.ListReadDto.ExpenseCategoryListItemDto;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Events.CategoryEvent;
import com.janek.app.Services.ExpenseCategoryService;
import com.janek.app.Utils.ExpenseCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/expense-manager/categories")
public class ExpenseCategoryController {

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Autowired
    private ExpenseCategoryMapper expenseCategoryMapper;

    private final RestTemplate restTemplate;

    @Value("${expense.manager.name}")
    private String expenseManagerName;

    public ExpenseCategoryController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseCategoryReadDto> getCategory(@PathVariable("id") UUID id) {
        Optional<ExpenseCategory> categoryOptional = expenseCategoryService.findCategoryById(id);
        if (categoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExpenseCategory category = categoryOptional.get();

        ExpenseCategoryReadDto categoryDto = ExpenseCategoryReadDto.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .budget(category.getBudget())  // Assuming budget is present in ExpenseCategory
                .build();

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @GetMapping(value = "/")
    public ResponseEntity<ExpenseCategoriesDto> getAllCategories() {
        List<ExpenseCategory> categories = expenseCategoryService.findAllCategories();
        List<ExpenseCategoryListItemDto> categoryDtos = categories.stream()
                .map(expenseCategoryMapper::mapToExpenseCategoryListItemDto)
                .collect(Collectors.toList());

        ExpenseCategoriesDto categoriesDto = ExpenseCategoriesDto.builder()
                .expenseCategories(categoryDtos)
                .build();

        return new ResponseEntity<>(categoriesDto, HttpStatus.OK);
    }

    @PostMapping(value = "/new")
    public ResponseEntity<ExpenseCategoryReadDto> createCategory(@RequestBody ExpenseCategoryCreateDto categoryCreateDto) {
        if (categoryCreateDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        ExpenseCategory newCategory = ExpenseCategory.builder()
                .id(UUID.randomUUID())
                .name(categoryCreateDto.getName())
                .description(categoryCreateDto.getDescription())
                .build();

        ExpenseCategory savedCategory = expenseCategoryService.saveCategory(newCategory);

        ExpenseCategoryReadDto categoryDto = expenseCategoryMapper.mapToExpenseCategoryReadDto(savedCategory);

        // send event to the expense management application
        sendCategoryEvent("ADD",savedCategory.getId());

        return new ResponseEntity<>(categoryDto, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/update/{id}")
    public ResponseEntity<ExpenseCategoryReadDto> updateCategory(@PathVariable UUID id, @RequestBody ExpenseCategoryUpdateDto categoryUpdateDto) {
        Optional<ExpenseCategory> categoryOptional = expenseCategoryService.findCategoryById(id);
        if (categoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExpenseCategory existingCategory = categoryOptional.get();
        existingCategory.setName(categoryUpdateDto.getName());
        existingCategory.setDescription(categoryUpdateDto.getDescription());

        ExpenseCategory updatedCategory = expenseCategoryService.saveCategory(existingCategory);

        ExpenseCategoryReadDto categoryDto = expenseCategoryMapper.mapToExpenseCategoryReadDto(updatedCategory);

        return new ResponseEntity<>(categoryDto, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ExpenseCategoryReadDto> deleteCategory(@PathVariable UUID id){
        Optional<ExpenseCategory> categoryOptional = expenseCategoryService.findCategoryById(id);
        if (categoryOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // send event to the expense management application
        sendCategoryEvent("REMOVE", id);

        expenseCategoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    private void sendCategoryEvent(String action, UUID expenseCategoryUUID){
        CategoryEvent categoryEvent = new CategoryEvent();
        categoryEvent.setAction(action);
        categoryEvent.setExpenseCategoryId(expenseCategoryUUID);
        String expenseManagementUri = loadBalancerClient
                .choose(expenseManagerName)
                .getUri().toString();
        String uri = expenseManagementUri + "/api/expense-manager/events/handle-category-event";

        System.out.println("sending category event to " + uri);

        // Send POST request to the elements management application
        restTemplate.postForEntity(uri, categoryEvent, Void.class);
    }
}
