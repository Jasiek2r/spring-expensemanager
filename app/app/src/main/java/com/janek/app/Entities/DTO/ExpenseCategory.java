package com.janek.app;


import java.util.UUID;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.io.Serializable;


@Builder
public class ExpenseCategory implements Serializable{
    @Getter
    private UUID id = UUID.randomUUID();
    @Getter @Setter
    private String name;
    @Getter @Setter
    private String description;
    @Getter @Setter
    private double budget;
    @Getter @Setter
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
                ", expenses=" + expenses +
                "}";
    }

}
