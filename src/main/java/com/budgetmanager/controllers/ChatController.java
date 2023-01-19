package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.ChatMessageDto;
import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.services.ChatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
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

    @GetMapping("/findAll")
    List<ChatMessage> getAllMessages() {
        return chatService.messageList();
    }
}
