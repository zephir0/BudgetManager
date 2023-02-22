package com.budgetmanager.user.entities;

import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.chat.entity.ChatMessage;
import com.budgetmanager.ticket.entity.Ticket;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.cache.annotation.Cacheable;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity

@Cacheable(cacheNames = "users")
@Table(name = "user")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "rolename", nullable = false)
    private UserRoles role;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<Ticket> tickets;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<ChatMessage> messages;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Budget> budget;

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public Collection<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }

    public Collection<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(Set<ChatMessage> messages) {
        this.messages = messages;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Collection<Budget> getBudget() {
        return budget;
    }

    public void setBudget(Collection<Budget> budget) {
        this.budget = budget;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
