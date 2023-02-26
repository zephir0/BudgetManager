package com.budgetmanager.user.exceptions;

public class IncorrectOldPasswordException extends RuntimeException {
    public IncorrectOldPasswordException(String message) {
        super(message);
    }
}
