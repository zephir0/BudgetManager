package com.budgetmanager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.cache.annotation.Cacheable;


import javax.persistence.*;
import java.util.Collection;

@Entity

@Cacheable(cacheNames = "users")
@Table(name = "user")
public class User {
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    UserRole userRole;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Budget> budget;



    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRoleId) {
        this.userRole = userRoleId;
    }

    public Long getId() {
        return id;
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
