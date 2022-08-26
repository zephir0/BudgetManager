package com.budgetmanager.services;

import com.budgetmanager.DTOs.UserRegisterDto;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        User mappedUser = map(userRegisterDto);
        userRepository.save(mappedUser);
    }

    public User map(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setLogin(userRegisterDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        return user;
    }
}
