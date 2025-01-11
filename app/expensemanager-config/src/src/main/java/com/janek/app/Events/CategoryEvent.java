package com.janek.app.Events;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class CategoryEvent {
    private String action;
    private UUID expenseCategoryId;
}