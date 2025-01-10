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

    public ExpenseCategory(UUID id, String name, String description, double budget){
        this.id = id;
        this.name = name;
        this.description = description;
        this.budget = budget;
    }
    public ExpenseCategory(){

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
