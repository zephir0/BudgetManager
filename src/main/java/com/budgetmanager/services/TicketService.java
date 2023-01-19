package com.budgetmanager.services;

import com.budgetmanager.DTOs.TicketDto;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.repositories.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;

    public TicketService(TicketRepository ticketRepository,
                         UserService userService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
    }

    public void createTicket(
            TicketDto ticketDto) {
        Ticket ticket = TicketDto.map(userService.getLoggedUser()
                .orElseThrow(
                        () -> new NotAuthorizedException("NOT AUTHORIZED.")
                ), ticketDto
        );
        ticketRepository.save(ticket);
    }

    public List<Ticket> showAllTickets() {
        return ticketRepository.findAllByUser(userService.getLoggedUser()
                .orElseThrow(
                        () -> new NotAuthorizedException("NOT AUTHORIZED")
                ));
    }
}
