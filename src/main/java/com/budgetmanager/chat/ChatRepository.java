package com.budgetmanager.chat;

import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.ticket.entity.Ticket;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@EnableCaching
public interface ChatRepository extends CrudRepository<ChatMessage, Long> {
    @Cacheable(cacheNames = "messages")
    List<ChatMessage> findByTicket(Ticket ticket);

    @Cacheable(cacheNames = "messages")
    List<ChatMessage> findAllByTicketId(Long ticketId);

    @Cacheable(cacheNames = "messages")
    void deleteByTicketId(Long ticketId);

    @CacheEvict(cacheNames = "messages", allEntries = true, beforeInvocation = true)
    @Override
    <S extends ChatMessage> S save(S entity);

    @CacheEvict(cacheNames = "messages", allEntries = true, beforeInvocation = true)
    @Override
    void delete(ChatMessage entity);

    @Cacheable(cacheNames = "messages")
    @Override
    Optional<ChatMessage> findById(@Param("id") Long id);
}
