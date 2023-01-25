package com.budgetmanager.services;

import com.budgetmanager.DTOs.ChatMessageDto;
import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.exceptions.MessageDoesntExistException;
import com.budgetmanager.exceptions.TicketDoesntExistException;
import com.budgetmanager.exceptions.TicketIsEmptyException;
import com.budgetmanager.exceptions.UserDoesntExistException;
import com.budgetmanager.repositories.ChatRepository;
import com.budgetmanager.repositories.TicketRepository;
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
        ChatMessage chatMessage = ChatMessageDto.map(ticketRepository.findById(ticketId).orElseThrow(() -> new TicketDoesntExistException("TICKET DOESNT EXIST")), chatMessageDto, userService.getLoggedUser().orElseThrow(() -> new UserDoesntExistException("USER DOESN'T EXIST.")));
        chatRepository.save(chatMessage);
    }

    @Transactional
    public void deleteAllByTicketId(Long ticketId) {
        List<ChatMessage> messages = chatRepository.findAllByTicketId(ticketId);
        if (messages != null && !messages.isEmpty()) {
            chatRepository.deleteAll(messages);
        } else {
            throw new TicketIsEmptyException("Messages doesn't exist");
        }
    }

    public List<ChatMessage> messageList(Long ticketId) {
        return chatRepository.findAllByUserAndTicketId(userService.getLoggedUser().orElseThrow(() -> new UserDoesntExistException("USER DOESN'T EXIST")), ticketId);
    }

    @Transactional
    public void deleteMessage(Long messageId) {
        ChatMessage message = chatRepository.findById(messageId)
                .orElseThrow(() -> new MessageDoesntExistException("Message doesn't exist"));
        chatRepository.delete(message);
    }
}
