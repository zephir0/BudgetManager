package com.budgetmanager.services;

import com.budgetmanager.DTOs.UserLoginDto;
import com.budgetmanager.DTOs.UserLoginDtoMapper;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<UserLoginDto> findByCredentialsLogin(String login) {
        return userRepository.findByLogin(login).map(UserLoginDtoMapper::map);
    }

    public Optional<User> findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    public Optional<User> getLoggedUser() {
        String loggedUserLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByLogin(loggedUserLogin);
    }
    public List<User> findAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }


    public Long getLoggedUserId() {
        return getLoggedUser().get().getId();
    }
}
