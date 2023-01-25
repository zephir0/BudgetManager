package com.budgetmanager.repositories;

import com.budgetmanager.entities.Ticket;
import com.budgetmanager.entities.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

@EnableCaching
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    @Cacheable(cacheNames = "budgets")
    List<Ticket> findAllByUser(User user);

    List<Ticket> findAllByUserId(Long userId);

}
