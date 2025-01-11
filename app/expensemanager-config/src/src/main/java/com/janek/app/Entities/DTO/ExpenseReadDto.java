package com.janek.app.Entities.DTO;

import com.janek.app.Entities.ExpenseCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter @Setter
/*
    DTO Class for Expense Object which
    is being used to return API data to the user.
 */
public class ExpenseReadDto {
    private UUID id;
    private String name;
    private String description;
    private double amount;
    private UUID categoryId;
}
