package com.budgetmanager.exceptions;

public class UserRolesNotFoundException extends RuntimeException {
    public UserRolesNotFoundException(String message) {
        super(message);
    }
}
