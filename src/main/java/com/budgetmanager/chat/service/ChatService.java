package com.budgetmanager.chat.service;

import com.budgetmanager.chat.ChatRepository;
import com.budgetmanager.chat.dto.ChatMessageDto;
import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.chat.exception.MessageDoesntExistException;
import com.budgetmanager.chat.mapper.ChatMessageMapper;
import com.budgetmanager.ticket.TicketRepository;
import com.budgetmanager.ticket.exceptions.TicketDoesntExistException;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.services.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatService {
    private final ChatRepository chatRepository;
    private final TicketRepository ticketRepository;
    private final UserService userService;

    public ChatService(ChatRepository chatRepository,
                       TicketRepository ticketRepository,
                       UserService userService) {
        this.chatRepository = chatRepository;
        this.ticketRepository = ticketRepository;
        this.userService = userService;
    }

    public void addMessage(Long ticketId,
                           ChatMessageDto chatMessageDto) {
        ChatMessage chatMessage = ChatMessageMapper
                .map(ticketRepository.findById(ticketId)
                        .orElseThrow(
                                () -> new TicketDoesntExistException("Ticket doesn't exist")), chatMessageDto, userService.getLoggedUser()
                );
        chatRepository.save(chatMessage);
    }

    @Transactional
    public void deleteAllByTicketId(Long ticketId) {
        List<ChatMessage> messages = chatRepository.findAllByTicketId(ticketId);
        if (messages != null && !messages.isEmpty()) {
            chatRepository.deleteAll(messages);
        }
    }

    public List<ChatMessage> messageList(Long ticketId) {
        return ticketRepository.findById(ticketId).map(ticket -> {
            if (!ticket.getUser().equals(userService.getLoggedUser()) || !userService.getLoggedUser().getRole().equals(UserRoles.ADMIN)) {
                throw new NotAuthorizedException("You are not authorized to see that ticket messages");
            } else
                return chatRepository.findAllByTicketId(ticketId);
        }).orElseThrow(() -> new TicketDoesntExistException("Ticket doesn't exist"));
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        ChatMessage message = chatRepository.findById(messageId).orElseThrow(() -> new MessageDoesntExistException("Message doesn't exist"));
        chatRepository.delete(message);
    }
}
