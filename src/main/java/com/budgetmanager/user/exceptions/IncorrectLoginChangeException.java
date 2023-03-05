package com.budgetmanager.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Sorry, the chosen login is already in use. Please choose a different login.")
public class IncorrectLoginChangeException extends RuntimeException {
    public IncorrectLoginChangeException(String message) {
        super(message);
    }
}
