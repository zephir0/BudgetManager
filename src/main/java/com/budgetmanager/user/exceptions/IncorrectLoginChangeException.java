package com.budgetmanager.user.exceptions;

public class IncorrectLoginChangeException extends RuntimeException {
    public IncorrectLoginChangeException(String message) {
        super(message);
    }
}
