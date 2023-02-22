package com.budgetmanager.ticket.controller;

import com.budgetmanager.ticket.service.TicketService;
import com.budgetmanager.ticket.dto.TicketDto;
import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.ticket.exceptions.TicketDoesntExistException;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping()
    ResponseEntity<String> createTicket(@RequestBody TicketDto ticketDto) throws UserNotLoggedInException {
        ticketService.createTicket(ticketDto);
        return new ResponseEntity<>("Ticket has been added", HttpStatus.OK);
    }


    @GetMapping("/findAll")
    ResponseEntity<List<Ticket>> showAllTickets() throws NotAuthorizedException {
        return ResponseEntity.ok(ticketService.showAllTickets());
    }

    @DeleteMapping("/{ticketId}")
    ResponseEntity<String> deleteTicket(@PathVariable("ticketId") long ticketId) throws UserNotLoggedInException, NotAuthorizedException, TicketDoesntExistException {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>("Ticket has been deleted", HttpStatus.OK);
    }
}
