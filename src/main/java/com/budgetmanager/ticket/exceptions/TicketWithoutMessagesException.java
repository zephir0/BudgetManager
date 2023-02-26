package com.budgetmanager.ticket.exceptions;

public class TicketWithoutMessagesException extends RuntimeException {
    public TicketWithoutMessagesException(String message) {
        super(message);
    }
}
