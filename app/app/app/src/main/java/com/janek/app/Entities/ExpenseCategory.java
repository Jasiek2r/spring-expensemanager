package com.janek.app.Entities;

import jakarta.persistence.*;
import java.util.UUID;
import java.util.Comparator;
import java.util.List;

import lombok.*;

import java.io.Serializable;


@Builder
@Entity
@Getter @Setter
public class ExpenseCategory implements Serializable{
    @Id
    @Column(name = "id", nullable = false)
    private UUID id = UUID.randomUUID();
    @Column(name="name", nullable = false)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "budget", nullable = false)
    private double budget;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Expense> expenses;

    private static Comparator<ExpenseCategory> byHashComparator = new Comparator<ExpenseCategory>() {
        @Override
        public int compare(ExpenseCategory o1, ExpenseCategory o2) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
        }
    };
    private static Comparator<ExpenseCategory> byNaturalOrderComparator = new Comparator<ExpenseCategory>() {
        @Override
        public int compare(ExpenseCategory o1, ExpenseCategory o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };

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

    public int compareByHash(ExpenseCategory o1, ExpenseCategory o2) {
        return this.byHashComparator.compare(o1, o2);
    }

    public int compareByNaturalOrder(ExpenseCategory o1, ExpenseCategory o2) {
        return this.byNaturalOrderComparator.compare(o1, o2);
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
