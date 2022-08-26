package com.budgetmanager.services;

import com.budgetmanager.DTOs.UserLoginDto;
import com.budgetmanager.DTOs.UserLoginDtoMapper;
import com.budgetmanager.repositories.UserRepository;
import org.springframework.stereotype.Service;

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
}
