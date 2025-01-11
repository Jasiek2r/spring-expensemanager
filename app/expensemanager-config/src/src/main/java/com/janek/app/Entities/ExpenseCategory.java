package com.janek.app.Entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.Comparator;
import java.util.List;

import lombok.*;

import java.io.Serializable;


@Builder
@Entity
@Table(name="categories")
@Getter @Setter
public class ExpenseCategory implements Serializable{
    @Id
    @Column(name = "id", nullable = false)
    private UUID id = UUID.randomUUID();
    @Column(name="name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "budget")
    private double budget;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;

    public ExpenseCategory(UUID id, String name, String description, double budget, List<Expense> expenses){
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
        this.expenses = expenses;
    }
    public ExpenseCategory(){

    }

    public void addExpense(Expense expense) {
        expense.setCategory(this);
        this.expenses.add(expense);
    }

    @Override
    public String toString() {
        return "ExpenseCategory{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", budget=" + budget +
                "}";
    }

}
