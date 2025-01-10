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