package com.budgetmanager.ticket.entity;

import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.user.entities.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Cacheable(cacheNames = "budgets")
@Table(name = "ticket")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ChatMessage> messages;
    private String subject;
    private String message;
    private String status;
    @Column(name = "updated_at")
    private String updatedAt;


    @Column(name = "created_at")
    private String createdAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getSubject() {
        return subject;
    }

    public Collection<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<ChatMessage> messages) {
        this.messages = messages;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
