package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.TicketDto;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.exceptions.TicketDoesntExistException;
import com.budgetmanager.exceptions.UserNotLoggedInException;
import com.budgetmanager.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            return new ResponseEntity<>("Ticket has been added", HttpStatus.OK);
        } catch (UserNotLoggedInException e) {
            return new ResponseEntity<>("You are not authorized", HttpStatus.UNAUTHORIZED);
        }

    }


    @GetMapping("/findAll")
    ResponseEntity<?> showAllTickets() {
        try {
            return ResponseEntity.ok(ticketService.showAllTickets());
        } catch (UserNotLoggedInException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User is not logged in");
        }
    }

    @DeleteMapping("/{ticketId}")
    ResponseEntity<String> deleteTicket(@PathVariable("ticketId") long ticketId) {
        try {
            ticketService.deleteTicket(ticketId);
            return new ResponseEntity<>("Ticket has been deleted", HttpStatus.OK);
        } catch (UserNotLoggedInException e) {
            return new ResponseEntity<>("User is not logged in", HttpStatus.UNAUTHORIZED);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>("You are not authorized to delete this ticket", HttpStatus.UNAUTHORIZED);
        } catch (TicketDoesntExistException e) {
            return new ResponseEntity<>("Ticket doesn't exist", HttpStatus.NOT_FOUND);
        }
    }
}
