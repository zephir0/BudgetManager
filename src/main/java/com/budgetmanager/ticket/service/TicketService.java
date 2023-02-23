package com.budgetmanager.ticket.service;

import com.budgetmanager.chat.service.ChatService;
import com.budgetmanager.ticket.TicketRepository;
import com.budgetmanager.ticket.dto.TicketDto;
import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.ticket.exceptions.TicketDoesntExistException;
import com.budgetmanager.ticket.mapper.TicketMapper;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.services.UserService;
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
        ticketRepository.save(TicketMapper.map(userService.getLoggedUser(), ticketDto));
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
