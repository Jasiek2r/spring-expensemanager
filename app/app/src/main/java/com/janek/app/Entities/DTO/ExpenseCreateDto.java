package com.janek.app.Entities.DTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Builder
@Getter @Setter
/*
    DTO Class for Expense Object which
    is being used to send create requests to the server.
    We only use settable fields here.
*/
public class ExpenseCreateDto {
    private String name;
    private String description;
    private double amount;
    private UUID categoryId;

    @Override
    public String toString() {
        return "ExpenseCreateDto{" +
                "name=" + name +
                ", description=" + description +
                ", amount=" + amount +
                ", categoryID=" + categoryId +
                "}";
    }
}
