package com.budgetmanager.ticket.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Ticket doesn't exist")
public class TicketDoesntExistException extends RuntimeException {
    public TicketDoesntExistException(String message) {
        super(message);
    }
}
