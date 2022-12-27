package com.budgetmanager.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
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
    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonBackReference
    UserRole userRoleId;

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Set<UserRole> allRoles = new HashSet<>();

<<<<<<< HEAD
    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Collection<Budget> budget;



=======
>>>>>>> main
    public UserRole getUserRoleId() {
        return userRoleId;
    }

    public Set<UserRole> getAllRoles() {
        return allRoles;
    }

    public void setAllRoles(Set<UserRole> allRoles) {
        this.allRoles = allRoles;
    }

    public void setUserRoleId(UserRole userId) {
        this.userRoleId = userId;
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
