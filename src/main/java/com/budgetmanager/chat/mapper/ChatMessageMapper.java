package com.budgetmanager.chat.mapper;

import com.budgetmanager.chat.dto.ChatMessageDto;
import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;

import java.time.LocalDateTime;

public class ChatMessageMapper {
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
}
