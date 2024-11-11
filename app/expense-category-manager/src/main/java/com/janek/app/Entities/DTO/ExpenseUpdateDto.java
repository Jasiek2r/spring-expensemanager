package com.janek.app.Entities.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
/*
    DTO Class for Expense Object which
    is being used to send update requests to the server.
    We only use updatable fields here.
 */
public class ExpenseUpdateDto {
    private String name;
    private String description;
    private double amount;

    @Override
    public String toString() {
        return "ExpenseUpdateDto{" +
                "name=" + name +
                ", description=" + description +
                ", amount=" + amount +
                "}";
    }
}
