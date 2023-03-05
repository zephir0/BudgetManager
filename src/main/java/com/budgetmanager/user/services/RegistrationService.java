package com.budgetmanager.user.services;

import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.dtos.UserRegisterDto;
import com.budgetmanager.user.exceptions.UserAlreadyExistException;
import com.budgetmanager.user.mappers.UserRegistrationMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationService(UserRepository userRepository,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        boolean userLoginExists = userRepository.existsByLogin(userRegisterDto.getLogin());
        if (userLoginExists)
            throw new UserAlreadyExistException("User with that login already exist in database");
        else {
            userRepository.save(UserRegistrationMapper.map(userRegisterDto, passwordEncoder));
        }
    }
}
