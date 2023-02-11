package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.ChatMessageDto;
import com.budgetmanager.entities.ChatMessage;
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
        chatService.addMessage(ticketId, messageDto);
        return new ResponseEntity<>("message sent", HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    List<ChatMessage> getMessagesByTicketId(@PathVariable("ticketId") Long ticketId) {
        return chatService.messageList(ticketId);
    }

    @DeleteMapping("/{messageId}")
    ResponseEntity<String> deleteMessage(@PathVariable("messageId") Long messageId) {
        chatService.deleteMessage(messageId);
        return new ResponseEntity<>("message deleted", HttpStatus.OK);
    }

}
