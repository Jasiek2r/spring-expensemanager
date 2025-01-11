package com.janek.app.Entities.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
}
