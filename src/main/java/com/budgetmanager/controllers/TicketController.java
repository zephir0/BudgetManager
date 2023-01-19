package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.TicketDto;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.services.TicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/ticket")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping()
    ResponseEntity<String> createTicket(@RequestBody TicketDto ticketDto) {
        ticketService.createTicket(ticketDto);
        return new ResponseEntity<>("ticket added", HttpStatus.OK);
    }

    @GetMapping("/findAll")
    List<Ticket> showAllTickets() {
        return ticketService.showAllTickets();
    }
}
