package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.ChatMessageDto;
import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.exceptions.MessageDoesntExistException;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.exceptions.TicketDoesntExistException;
import com.budgetmanager.exceptions.UserNotLoggedInException;
import com.budgetmanager.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {
    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping("/{ticketId}")
    ResponseEntity<String> sendMessage(@PathVariable("ticketId") Long ticketId,
                                       @RequestBody ChatMessageDto messageDto) {
        try {
            chatService.addMessage(ticketId, messageDto);
            return new ResponseEntity<>("Message sent", HttpStatus.OK);
        } catch (UserNotLoggedInException e) {
            return new ResponseEntity<>("You are not logged in.", HttpStatus.NOT_FOUND);
        } catch (TicketDoesntExistException e) {
            return new ResponseEntity<>("Ticket doesn't exist", HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/{ticketId}")
    ResponseEntity<List<ChatMessage>> getMessagesByTicketId(@PathVariable("ticketId") Long ticketId) {
        try {
            return ResponseEntity.ok(chatService.messageList(ticketId));
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        } catch (TicketDoesntExistException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{messageId}")
    ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        try {
            chatService.deleteMessage(messageId);
            return new ResponseEntity<>("Message deleted", HttpStatus.OK);
        } catch (MessageDoesntExistException e) {
            return new ResponseEntity<>("You cannot delete message that doesn't exist", HttpStatus.OK);
        }

    }

}
