package com.janek.app.Entities.DTO.ListReadDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

@Builder
@Getter @Setter
/*
    DTO Object which holds a list of simplified Expense Objects
    that do contain only name and ID
 */
public class ExpensesReadDto {
    @Singular
    private List<ExpenseListItemDto> expenses;
}
