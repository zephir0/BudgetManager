package com.budgetmanager.chat.controller;

import com.budgetmanager.chat.dto.ChatMessageDto;
import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.chat.exception.MessageDoesntExistException;
import com.budgetmanager.chat.service.ChatService;
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
@RequestMapping("/api/chat")
@Tag(name = "Chat", description = "Endpoints for sending and receiving chat messages")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/{ticketId}")
    @Operation(summary = "Send a message", description = "Sends a chat message for the specified ticket")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, name = "ticketId", description = "Ticket ID", required = true),
            @Parameter(in = ParameterIn.DEFAULT, name = "messageDto", description = "Message data", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Message sent"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> sendMessage(@PathVariable("ticketId") Long ticketId,
                                              @RequestBody ChatMessageDto messageDto) throws UserNotLoggedInException, TicketDoesntExistException {
        chatService.addMessage(ticketId, messageDto);
        return new ResponseEntity<>("Message sent", HttpStatus.CREATED);
    }

    @GetMapping("/{ticketId}")
    @Operation(summary = "Get messages by ticket ID", description = "Returns a list of all chat messages for the specified ticket")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, name = "ticketId", description = "Ticket ID", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of messages"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Ticket not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<ChatMessage>> getMessagesByTicketId(@PathVariable("ticketId") Long ticketId) throws NotAuthorizedException, TicketDoesntExistException {
        return ResponseEntity.ok(chatService.messageList(ticketId));
    }

    @DeleteMapping("/{messageId}")
    @Operation(summary = "Delete a message", description = "Deletes a chat message by ID")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, name = "messageId", description = "Message ID", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message deleted"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        try {
            chatService.deleteMessage(messageId);
            return new ResponseEntity<>("Message deleted", HttpStatus.OK);
        } catch (MessageDoesntExistException e) {
            return new ResponseEntity<>("You cannot delete message that doesn't exist", HttpStatus.OK);
        }
    }
}