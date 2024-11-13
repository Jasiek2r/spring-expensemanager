package com.janek.app.Events;

import com.janek.app.Entities.DTO.ExpenseCategoryReadDto;
import com.janek.app.Entities.ExpenseCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class InitializationEvent {
    private List<ExpenseCategoryReadDto> expenseCategories;
    public InitializationEvent(){
        this.expenseCategories = new ArrayList<>();
    }
    public InitializationEvent(List<ExpenseCategoryReadDto> expenseCategories){
        this.expenseCategories = expenseCategories;
    }
}
