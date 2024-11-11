package com.janek.app.Entities.DTO.ListReadDto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/*
    Helper object for ExpenseCategoriesDto
    A part of the list
 */
@Getter @Setter
@Builder
public class ExpenseCategoryListItemDto {
    private UUID id;
    private String name;
}
