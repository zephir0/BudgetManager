package com.budgetmanager.ticket.exceptions;

public class TicketDoesntExistException extends RuntimeException {
    public TicketDoesntExistException(String message) {
        super(message);
    }
}
