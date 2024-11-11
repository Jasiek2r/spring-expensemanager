package com.janek.app.Entities.DTO.ListReadDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Builder
@Getter @Setter
/*
    Helper object for ExpensesReadDto
    A part of the list
 */
public class ExpenseListItemDto {
    private UUID id;
    private String name;
}
