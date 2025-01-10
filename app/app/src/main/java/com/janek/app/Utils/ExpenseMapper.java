package com.janek.app.Utils;

import com.janek.app.Entities.DTO.ExpenseReadDto;
import com.janek.app.Entities.DTO.ListReadDto.ExpenseListItemDto;
import com.janek.app.Entities.Expense;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseReadDto mapToExpenseReadDto(Expense expense) {
        return ExpenseReadDto.builder()
                .id(expense.getId())
                .name(expense.getName())
                .description(expense.getDescription())
                .amount(expense.getAmount())
                .categoryId(expense.getCategory().getId())
                .build();
    }

    public ExpenseListItemDto mapToExpenseListItemDto(Expense expense) {
        return ExpenseListItemDto.builder()
                .id(expense.getId())
                .name(expense.getName())
                .build();
    }
}
