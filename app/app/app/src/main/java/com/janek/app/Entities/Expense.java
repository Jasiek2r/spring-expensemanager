package com.janek.app.Entities;
import java.util.UUID;
import java.util.Comparator;


import com.janek.app.Entities.ExpenseCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder;
import java.io.Serializable;


@Entity
@Table(name="expenses")
@Builder
@Getter @Setter
public class Expense implements Serializable{
    @Id
    private UUID id = UUID.randomUUID(); //Client-generated UUID
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="description")
    private String description;
    @Column(name="amount", nullable = false)
    private double amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private ExpenseCategory category;

    public Expense(UUID id, String name, String description, double amount, ExpenseCategory category){
        this.id = id;
        this.name = name;
        this.description = description;
        this.amount = amount;
        this.category = category;
    }
    public Expense(){

    }

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