package com.budgetmanager.services;

import com.budgetmanager.DTOs.TicketDto;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.exceptions.TicketDoesntExistException;
import com.budgetmanager.repositories.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserService userService;
    private final ChatService chatService;

    public TicketService(TicketRepository ticketRepository,
                         UserService userService,
                         ChatService chatService) {
        this.ticketRepository = ticketRepository;
        this.userService = userService;
        this.chatService = chatService;
    }

    public void createTicket(TicketDto ticketDto) {
        Ticket ticket = TicketDto.map(userService.getLoggedUser().orElseThrow(() -> new NotAuthorizedException("NOT AUTHORIZED.")), ticketDto);
        ticketRepository.save(ticket);
    }

    public List<Ticket> showAllTickets() {
        return ticketRepository.findAllByUser(userService.getLoggedUser().orElseThrow(() -> new NotAuthorizedException("NOT AUTHORIZED")));
    }

    @Transactional
    public void deleteTicket(Long ticketId) {
        ticketRepository.findById(ticketId).map((ticket) -> {
                    if (userService.getLoggedUserId().equals(ticket.getUser().getId())) {
//            || ticket.getUser().getUserRole().getDescription().equals("ADMIN")) {
                        chatService.deleteAllByTicketId(ticketId);
                        ticketRepository.deleteById(ticketId);
                    } else {
                        throw new NotAuthorizedException("You are not a creator of that ticket");
                    }

                    return ticket;
                }).

                orElseThrow(() -> new

                        TicketDoesntExistException("Ticket doesn't exist"));
    }
}
