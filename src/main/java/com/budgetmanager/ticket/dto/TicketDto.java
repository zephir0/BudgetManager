package com.budgetmanager.ticket.dto;

import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.user.entities.User;

import java.time.LocalDateTime;

public class TicketDto {
    private final String subject;
    private final String message;

    public TicketDto(String subject,
                     String message) {
        this.subject = subject;
        this.message = message;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

}
