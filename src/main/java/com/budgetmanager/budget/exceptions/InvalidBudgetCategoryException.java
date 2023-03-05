package com.budgetmanager.budget.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Invalid or missing budget category.")
public class InvalidBudgetCategoryException extends RuntimeException {
    public InvalidBudgetCategoryException(String message) {
        super(message);
    }
}
