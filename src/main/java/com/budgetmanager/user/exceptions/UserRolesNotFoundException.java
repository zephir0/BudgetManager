package com.budgetmanager.user.exceptions;

public class UserRolesNotFoundException extends RuntimeException {
    public UserRolesNotFoundException(String message) {
        super(message);
    }
}
