package com.budgetmanager.chat.exception;

public class MessageDoesntExistException extends RuntimeException {
    public MessageDoesntExistException(String message) {
        super(message);
    }
}
