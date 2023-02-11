package com.budgetmanager.repositories;

import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.entities.User;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

@EnableCaching
public interface ChatRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findByTicket(Ticket ticket);


    List<ChatMessage> findAllByTicketId(Long ticketId);


    List<ChatMessage> findAllByUserAndTicketId(User user,
                                               Long ticket);


    void deleteByTicketId(Long ticketId);
}
