package com.budgetmanager.chat.controller;

import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.chat.dto.ChatMessageDto;
import com.budgetmanager.chat.service.ChatService;
import com.budgetmanager.chat.exception.MessageDoesntExistException;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.ticket.exceptions.TicketDoesntExistException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
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
                                       @RequestBody ChatMessageDto messageDto) throws UserNotLoggedInException, TicketDoesntExistException {
        chatService.addMessage(ticketId, messageDto);
        return new ResponseEntity<>("Message sent", HttpStatus.OK);
    }

    @GetMapping("/{ticketId}")
    ResponseEntity<List<ChatMessage>> getMessagesByTicketId(@PathVariable("ticketId") Long ticketId) throws NotAuthorizedException, TicketDoesntExistException {
            return ResponseEntity.ok(chatService.messageList(ticketId));

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
