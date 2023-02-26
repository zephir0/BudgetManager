package com.budgetmanager.budget.exceptions;

public class EmptyBudgetCategoryException extends RuntimeException {
    public EmptyBudgetCategoryException(String message) {
        super(message);
    }
}
