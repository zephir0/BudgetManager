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
    @Cacheable(cacheNames = "budgets")
    List<ChatMessage> findByTicket(Ticket ticket);

    @Cacheable(cacheNames = "budgets")
    List<ChatMessage> findAllByTicketId(Long ticketId);

    @Cacheable(cacheNames = "budgets")
    List<ChatMessage> findAllByUserAndTicketId(User user,
                                               Long ticket);

    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    void deleteByTicketId(Long ticketId);
}
