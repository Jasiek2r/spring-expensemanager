package com.janek.app.Entities.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ExpenseCategoryUpdateDto {
    private String name;
    private String description;
    private double amount;
}
