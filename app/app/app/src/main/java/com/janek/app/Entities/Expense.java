package com.janek.app.Entities;
import java.util.UUID;
import java.util.Comparator;


import com.janek.app.Entities.ExpenseCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.Builder;
import java.io.Serializable;

@Builder
@Entity
@Table(name="expenses")
public class Expense implements Serializable{
    @Getter @Id
    private UUID id = UUID.randomUUID(); //Client-generated UUID
    @Getter @Setter @Column(name="name", nullable = false)
    private String name;
    @Getter @Setter @Column(name="description")
    private String description;
    @Getter @Setter @Column(name="amount", nullable = false)
    private double amount;
    @Getter @Setter @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ExpenseCategory category;

    private static Comparator<Expense> byHashComparator = new Comparator<Expense>() {
            @Override
            public int compare(Expense o1, Expense o2) {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            };
    };
    
    private static Comparator<Expense> byNaturalOrderComparator = new Comparator<Expense>() {
        @Override
        public int compare(Expense o1, Expense o2) {
            return o1.getName().compareTo(o2.getName());
        }
    };


    public int compareByHash(Expense o1, Expense o2) {
        return this.byHashComparator.compare(o1, o2);
    }

    public int compareByNaturalOrder(Expense o1, Expense o2) {
        return this.byNaturalOrderComparator.compare(o1, o2);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", name=" + name +
                ", description=" + description +
                ", amount=" + amount +
                ", category=" + category.getName() +
                "}";
    }

}