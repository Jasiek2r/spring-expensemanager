package com.janek.app.Entities.DTO;

import com.janek.app.Entities.Expense;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Builder
@Getter @Setter
/*
    DTO Class for ExpenseCategory Object which
    is being used to return API data to the user.
 */
public class ExpenseCategoryReadDto {

    private UUID id;
    private String name;
    private String description;
    private double budget;
    private List<UUID> expenseIds;
}
