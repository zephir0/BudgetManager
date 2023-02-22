package com.budgetmanager.chat.entity;

import com.budgetmanager.ticket.entity.Ticket;
import com.budgetmanager.user.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;

@Entity
@Cacheable(cacheNames = "budgets")
@Table(name = "chat")
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    @JsonBackReference
    private Ticket ticket;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
    private String message;
    private String createdAt;
    private boolean isAdmin;

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
