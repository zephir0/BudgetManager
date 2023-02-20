package com.budgetmanager.repositories;

import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.entities.Ticket;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

@EnableCaching
public interface ChatRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findByTicket(Ticket ticket);


    List<ChatMessage> findAllByTicketId(Long ticketId);


    void deleteByTicketId(Long ticketId);
}
