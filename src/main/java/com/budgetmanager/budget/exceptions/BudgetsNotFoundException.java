package com.budgetmanager.budget.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "There is no budgets.")
public class BudgetsNotFoundException extends RuntimeException {
    public BudgetsNotFoundException(String message) {
        super(message);
    }
}
