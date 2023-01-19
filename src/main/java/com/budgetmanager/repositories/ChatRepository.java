package com.budgetmanager.repositories;

import com.budgetmanager.entities.ChatMessage;
import com.budgetmanager.entities.Ticket;
import com.budgetmanager.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends CrudRepository<ChatMessage, Long> {
    List<ChatMessage> findByTicket(Ticket ticket);


    List<ChatMessage> findAllByUser(User user);
}
