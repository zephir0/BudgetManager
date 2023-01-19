package com.budgetmanager.services;

import com.budgetmanager.DTOs.ChatMessageDto;
import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.exceptions.TicketDoesntExistException;
import com.budgetmanager.exceptions.UserDoesntExistException;
import com.budgetmanager.repositories.ChatRepository;
import com.budgetmanager.repositories.TicketRepository;
import org.springframework.stereotype.Service;

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
        ChatMessage chatMessage = ChatMessageDto.map(ticketRepository.findById(
                                ticketId).
                        orElseThrow(
                                () -> new TicketDoesntExistException("TICKET DOESNT EXIST")
                        )
                , chatMessageDto,
                userService.getLoggedUser()
                        .orElseThrow(
                                () -> new UserDoesntExistException("USER DOESN'T EXIST."))
        );
        chatRepository.save(chatMessage);
    }

    public List<ChatMessage> messageList() {
        return chatRepository.findAllByUser(userService.getLoggedUser()
                .orElseThrow(
                        () -> new UserDoesntExistException("USER DOESN'T EXIST")
                ));
    }
}
