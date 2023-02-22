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

    public static Ticket map(User user,
                             TicketDto ticketDto) {
        Ticket ticket = new Ticket();
        ticket.setUser(user);
        ticket.setSubject(ticketDto.getSubject());
        ticket.setMessage(ticketDto.getMessage());
        ticket.setCreatedAt(LocalDateTime.now().toString());
        ticket.setUpdatedAt(LocalDateTime.now().toString());
        return ticket;
    }
}
