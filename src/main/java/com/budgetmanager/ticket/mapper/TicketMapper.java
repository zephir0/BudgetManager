package com.budgetmanager.ticket.mapper;

import com.budgetmanager.ticket.dto.TicketDto;
import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.user.entities.User;

import java.time.LocalDateTime;

public class TicketMapper {
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
