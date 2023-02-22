package com.budgetmanager.user.services;

import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.dtos.UserRegisterDto;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.UserAlreadyExistException;
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
        if (checkIfUserExist(userRegisterDto.getLogin()))
            throw new UserAlreadyExistException("User with that login already exist in database");
        else {
            userRepository.save(map(userRegisterDto));
        }
    }

    public boolean checkIfUserExist(String login) {
        return userRepository.existsByLogin(login);
    }

    public User map(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setLogin(userRegisterDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        addRoleToUser(user);
        return user;
    }

    public void addRoleToUser(User user) {
        user.setRole(UserRoles.USER);
    }


}
