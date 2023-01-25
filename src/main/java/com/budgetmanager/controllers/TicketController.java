package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.TicketDto;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.exceptions.TicketDoesntExistException;
import com.budgetmanager.services.TicketService;
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
    ResponseEntity<String> createTicket(@RequestBody TicketDto ticketDto) {
        try {
            ticketService.createTicket(ticketDto);
            return new ResponseEntity<>("ticket added", HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>("you are not authorized", HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("/findAll")
    List<Ticket> showAllTickets() {
        return ticketService.showAllTickets();
    }

    @DeleteMapping("/{ticketId}")
    ResponseEntity<String> deleteTicket(@PathVariable("ticketId") long ticketId) {
        try {
            ticketService.deleteTicket(ticketId);
            return new ResponseEntity<>("ticket deleted", HttpStatus.OK);
        } catch (TicketDoesntExistException e) {
            return new ResponseEntity<>("ticket doesn't exist", HttpStatus.NOT_FOUND);

        }
    }
}
