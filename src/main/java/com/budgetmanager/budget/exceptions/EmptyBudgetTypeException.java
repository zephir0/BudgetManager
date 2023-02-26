package com.budgetmanager.budget.exceptions;

public class EmptyBudgetTypeException extends RuntimeException {
    public EmptyBudgetTypeException(String message) {
        super(message);
    }
}
