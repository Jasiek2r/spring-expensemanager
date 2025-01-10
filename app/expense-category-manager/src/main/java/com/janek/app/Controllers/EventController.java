package com.janek.app.Controllers;

import com.janek.app.Entities.DTO.ExpenseCategoryReadDto;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Events.InitializationEvent;
import com.janek.app.Services.ExpenseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/expense-manager/events")
public class EventController {
    @Autowired
    private ExpenseCategoryService categoryService;

    public EventController(ExpenseCategoryService categoryService){
        this.categoryService = categoryService;
    }

    @PostMapping("/handle-initialization-event")
    public ResponseEntity<Void> handleInitializationEvent(@RequestBody InitializationEvent event){

        //Fetch the list from request response
        List<ExpenseCategoryReadDto> expenseCategoryListDto = event.getExpenseCategories();

        //Convert back to expense categories
        List<ExpenseCategory> expenseCategoryList = expenseCategoryListDto.stream().map(
                element -> ExpenseCategory.builder()
                        .id(element.getId())
                        .name(element.getName())
                        .description(element.getDescription())
                        .budget(element.getBudget())
                        .build()
        ).collect(Collectors.toList());

        //Save the expense categories into the repository through an autowired service
        for(ExpenseCategory expenseCategory : expenseCategoryList){
            categoryService.saveCategory(expenseCategory);
        }
        categoryService.flush();

        return ResponseEntity.ok().build();
    }
}
