package com.budgetmanager.budget.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "You need to insert a value.")
public class InvalidBudgetValueException extends RuntimeException {
    public InvalidBudgetValueException(String message) {
        super(message);
    }
}
