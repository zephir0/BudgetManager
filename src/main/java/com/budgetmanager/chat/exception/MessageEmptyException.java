package com.budgetmanager.chat.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "Message cannot be empty")
public class MessageEmptyException extends RuntimeException {
    public MessageEmptyException(String message) {
        super(message);
    }
}
