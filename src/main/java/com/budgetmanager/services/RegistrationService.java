package com.budgetmanager.services;

import com.budgetmanager.DTOs.UserRegisterDto;
import com.budgetmanager.entities.User;
import com.budgetmanager.entities.UserRole;
import com.budgetmanager.exceptions.UserAlreadyExistException;
import com.budgetmanager.repositories.RoleRepository;
import com.budgetmanager.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public RegistrationService(UserRepository userRepository,
                               PasswordEncoder passwordEncoder,
                               RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public void registerUser(UserRegisterDto userRegisterDto) {
        if (checkIfUserExist(userRegisterDto)) {
            throw new UserAlreadyExistException("User already exist in database");
        } else {
            User mappedUser = map(userRegisterDto);
            userRepository.save(mappedUser);
        }

    }

    public boolean checkIfUserExist(UserRegisterDto userRegisterDto) {
        return userRepository.existsByLogin(userRegisterDto.getLogin());
    }

    public User map(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setLogin(userRegisterDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        addRoleToUser(user);
        return user;
    }

    public void addRoleToUser(User user) {
        Optional<UserRole> userRolesRepositoryByDescription = roleRepository.findByDescription("ADMIN");
        user.setUserRoleId(userRolesRepositoryByDescription.get());
    }


}
