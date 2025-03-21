package com.janek.app.Controllers;


import com.janek.app.Entities.DTO.ListReadDto.ExpenseListItemDto;
import com.janek.app.Entities.DTO.ListReadDto.ExpensesReadDto;
import com.janek.app.Entities.ExpenseCategory;
import com.janek.app.Services.ExpenseCategoryService;
import com.janek.app.Utils.ExpenseMapper;
import org.springframework.web.bind.annotation.RestController;
import com.janek.app.Entities.DTO.ExpenseCreateDto;
import com.janek.app.Entities.DTO.ExpenseReadDto;
import com.janek.app.Entities.Expense;
import com.janek.app.Services.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/expense-manager/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ExpenseCategoryService expenseCategoryService;

    private ExpenseMapper expenseMapper;

    public ExpenseController(ExpenseMapper expenseMapper){
        this.expenseMapper = expenseMapper;
    }

    //Fetches an expense given its id
    @GetMapping(value = "/{id}")
    public ResponseEntity<ExpenseReadDto> getExpense(@PathVariable("id") UUID id){
        Optional<Expense> serverResponse = expenseService.findExpenseById(id);
        //If the entered id is not valid, return a 404 NOT FOUND
        if(serverResponse.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //otherwise return a DTO object
        Expense fetchedExpense = serverResponse.get();
        ExpenseReadDto expenseDto = expenseMapper.mapToExpenseReadDto(fetchedExpense);

        return new ResponseEntity<>(expenseDto, HttpStatus.OK);
    }

    //Fetches all expenses
    @GetMapping(value = "/")
    public ResponseEntity<ExpensesReadDto> getAllExpenses() {
        List<Expense> expenses = expenseService.findAllExpenses();
        List<ExpenseListItemDto> expenseDtos = expenses.stream()
                .map(expenseMapper::mapToExpenseListItemDto)
                .collect(Collectors.toList());

        ExpensesReadDto expensesReadDto = ExpensesReadDto.builder()
                .expenses(expenseDtos) // Setting the list of expense items
                .build();

        return new ResponseEntity<>(expensesReadDto, HttpStatus.OK);
    }

    //Fetches all expenses that belong to a category with the provided categoryId
    @GetMapping(value = "/category/{id}")
    public ResponseEntity<ExpensesReadDto> getExpensesByCategory(@PathVariable UUID id){
        // Fetch a category if it is possible
        Optional<ExpenseCategory> requestedCategoryResult = expenseCategoryService.findCategoryById(id);
        //Return a 404 NOT FOUND if the category with a specified id is not present
        if(requestedCategoryResult.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        //otherwise return expenses in that category as DTO objects
        ExpenseCategory requestedCategory = requestedCategoryResult.get();
        List<Expense> expenses = expenseService.findExpensesByCategory(requestedCategory);
        List<ExpenseListItemDto> expenseDtos = expenses.stream()
                .map(expenseMapper::mapToExpenseListItemDto)
                .collect(Collectors.toList());

        ExpensesReadDto expensesReadDto = ExpensesReadDto.builder()
                .expenses(expenseDtos) // Setting the list of expense items
                .build();
        return new ResponseEntity<>(expensesReadDto, HttpStatus.OK);
    }

    //creates a new expense
    @PostMapping(value = "/new")  // Corrected the mapping to use POST for creating a new expense
    public ResponseEntity<ExpenseReadDto> createExpense(@RequestBody ExpenseCreateDto expenseCreateDto) {
        // Validate the incoming DTO
        if (expenseCreateDto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST); // 400 BAD REQUEST
        }

        Optional<ExpenseCategory> fetchResult = expenseCategoryService.findCategoryById(expenseCreateDto.getCategoryId());
        if(fetchResult.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
        }

        ExpenseCategory category = fetchResult.get();

        // Create a new Expense entity from the DTO
        Expense newExpense = Expense.builder()
                .id(UUID.randomUUID())
                .name(expenseCreateDto.getName())
                .description(expenseCreateDto.getDescription())
                .amount(expenseCreateDto.getAmount())
                .category(category)
                .build();

        // Save the new expense using the ExpenseService
        Expense savedExpense = expenseService.saveExpense(newExpense);

        // Map the saved entity to a DTO
        ExpenseReadDto expenseDto = expenseMapper.mapToExpenseReadDto(savedExpense);

        return new ResponseEntity<>(expenseDto, HttpStatus.CREATED); // 201 CREATED
    }

    //updates an expense with a given id
    @PatchMapping(value = "/update/{id}")
    public ResponseEntity<ExpenseReadDto> updateExpense(@PathVariable UUID id, @RequestBody ExpenseCreateDto expenseUpdateDto) {
        // Fetch the existing expense by ID
        Optional<Expense> existingExpenseOptional = expenseService.findExpenseById(id);
        if (existingExpenseOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
        }

        // Get the existing expense
        Expense existingExpense = existingExpenseOptional.get();
        UUID categoryId = existingExpense.getCategory().getId();
        Optional<ExpenseCategory> fetchResult = expenseCategoryService.findCategoryById(categoryId);

        if(fetchResult.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        ExpenseCategory fetchedCategory = fetchResult.get();

        // Update the expense properties based on the provided DTO
        existingExpense.setName(expenseUpdateDto.getName());
        existingExpense.setDescription(expenseUpdateDto.getDescription());
        existingExpense.setAmount(expenseUpdateDto.getAmount());
        existingExpense.setCategory(fetchedCategory);

        // Save the updated expense
        Expense updatedExpense = expenseService.saveExpense(existingExpense);

        // Map the updated entity to a DTO
        ExpenseReadDto expenseDto = expenseMapper.mapToExpenseReadDto(updatedExpense);

        return new ResponseEntity<>(expenseDto, HttpStatus.OK); // 200 OK
    }

    //deletes an expense with a given id
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ExpenseReadDto> deleteExpense(@PathVariable UUID id){
        Optional<Expense> existingExpenseOptional = expenseService.findExpenseById(id);
        if (existingExpenseOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 NOT FOUND
        }
        expenseService.deleteExpense(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}

