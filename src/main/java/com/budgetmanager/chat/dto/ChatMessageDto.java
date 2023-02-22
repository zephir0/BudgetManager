package com.budgetmanager.chat.dto;

import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;

import java.time.LocalDateTime;

public class ChatMessageDto {
    private final String message;
    private final LocalDateTime createdAt;
    private final boolean isAdmin;

    public ChatMessageDto(String message,
                          LocalDateTime createdAt,
                          boolean isAdmin) {
        this.message = message;
        this.createdAt = createdAt;
        this.isAdmin = isAdmin;
    }

    public static ChatMessage map(Ticket ticket,
                                  ChatMessageDto chatMessageDto,
                                  User user) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setAdmin(user.getRole().equals(UserRoles.ADMIN));
        chatMessage.setTicket(ticket);
        chatMessage.setUser(user);
        chatMessage.setMessage(chatMessageDto.getMessage());
        chatMessage.setCreatedAt(LocalDateTime.now().toString());
        return chatMessage;
    }


    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }
}