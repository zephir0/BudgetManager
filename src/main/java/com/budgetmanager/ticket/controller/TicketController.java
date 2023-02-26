package com.budgetmanager.ticket.controller;

import com.budgetmanager.ticket.service.TicketService;
import com.budgetmanager.ticket.dto.TicketDto;
import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.ticket.exceptions.TicketDoesntExistException;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@Tag(name = "Ticket", description = "Endpoints for creating and deleting tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping()
    @Operation(summary = "Create a ticket", description = "Creates a new ticket with the specified data")
    @Parameters({
            @Parameter(in = ParameterIn.DEFAULT, name = "ticketDto", description = "Ticket data", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ticket created"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> createTicket(@RequestBody TicketDto ticketDto) throws UserNotLoggedInException {
        ticketService.createTicket(ticketDto);
        return new ResponseEntity<>("Ticket has been created", HttpStatus.CREATED);
    }

    @GetMapping("/findAll")
    @Operation(summary = "Show all tickets", description = "Returns a list of all tickets")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of tickets"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<Ticket>> showAllTickets() throws NotAuthorizedException {
        return ResponseEntity.ok(ticketService.showAllTickets());
    }

    @DeleteMapping("/{ticketId}")
    @Operation(summary = "Delete a ticket", description = "Deletes a ticket by ID")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, name = "ticketId", description = "Ticket ID", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ticket deleted"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteTicket(@PathVariable("ticketId") long ticketId) throws UserNotLoggedInException, NotAuthorizedException, TicketDoesntExistException {
        ticketService.deleteTicket(ticketId);
        return new ResponseEntity<>("Ticket has been deleted", HttpStatus.OK);
    }
}