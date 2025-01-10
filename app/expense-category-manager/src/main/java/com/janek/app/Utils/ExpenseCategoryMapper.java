package com.janek.app.Utils;

import com.janek.app.Entities.DTO.ExpenseCategoryReadDto;
import com.janek.app.Entities.DTO.ListReadDto.ExpenseCategoryListItemDto;
import com.janek.app.Entities.ExpenseCategory;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryMapper {
    public ExpenseCategoryReadDto mapToExpenseCategoryReadDto(ExpenseCategory expenseCategory) {
        return ExpenseCategoryReadDto.builder()
                .id(expenseCategory.getId())
                .name(expenseCategory.getName())
                .description(expenseCategory.getDescription())
                .budget(expenseCategory.getBudget())
                .build();
    }

    public ExpenseCategoryListItemDto mapToExpenseCategoryListItemDto(ExpenseCategory expenseCategory) {
        return ExpenseCategoryListItemDto.builder()
                .id(expenseCategory.getId())
                .name(expenseCategory.getName())
                .build();
    }
}
