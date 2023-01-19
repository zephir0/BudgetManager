package com.budgetmanager.repositories;

import com.budgetmanager.entities.Ticket;
import com.budgetmanager.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Long> {
    List<Ticket> findAllByUser(User user);
}
