package com.budgetmanager.ticket;

import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.user.entities.User;
import org.springframework.cache.annotation.CacheEvict;
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

    @Cacheable(cacheNames = "budgets")
    List<Ticket> findAll();

    @CacheEvict(cacheNames = "budgets", allEntries = true, beforeInvocation = true)
    @Override
    <S extends Ticket> S save(S entity);
}
