package com.budgetmanager.budget.exceptions;

public class BudgetDoesntExistException extends RuntimeException {
    public BudgetDoesntExistException(String message) {
        super(message);
    }
}
