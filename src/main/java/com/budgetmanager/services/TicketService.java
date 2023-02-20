package com.budgetmanager.services;

import com.budgetmanager.DTOs.TicketDto;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.entities.UserRoles;
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
        ticketRepository.save(TicketDto.map(userService.getLoggedUser(), ticketDto));
    }

    public List<Ticket> showAllTickets() {
        return userService.getLoggedUser().getRole().equals(UserRoles.ADMIN) ? ticketRepository.findAll() : ticketRepository.findAllByUser(userService.getLoggedUser());
    }

    @Transactional
    public void deleteTicket(Long ticketId) {
        ticketRepository.findById(ticketId).ifPresentOrElse((ticket) -> {
            if (userService.getLoggedUser().getId().equals(ticket.getUser().getId()) || ticket.getUser().getRole().equals(UserRoles.ADMIN)) {
                chatService.deleteAllByTicketId(ticketId);
                ticketRepository.deleteById(ticketId);
            } else {
                throw new NotAuthorizedException("You are not a creator of that ticket");
            }
        }, () -> {
            throw new TicketDoesntExistException("Ticket doesn't exist");
        });
    }
}
