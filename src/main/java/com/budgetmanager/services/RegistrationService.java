package com.budgetmanager.services;

import com.budgetmanager.DTOs.UserRegisterDto;
import com.budgetmanager.entities.User;
import com.budgetmanager.entities.UserRole;
import com.budgetmanager.exceptions.UserRolesNotFoundException;
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
        User mappedUser = map(userRegisterDto);
        userRepository.save(mappedUser);
    }

    public User map(UserRegisterDto userRegisterDto) {
        User user = new User();
        Optional<UserRole> userRolesRepositoryByDescription = roleRepository
                .findByDescription("ADMIN");
        userRolesRepositoryByDescription.ifPresentOrElse(
                role -> user
                        .getAllRoles()
                        .add(role),
                () -> {
                    throw new UserRolesNotFoundException("User role not found.");
                });
        user.setLogin(userRegisterDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setUserRoleId(userRolesRepositoryByDescription.get());
        return user;
    }
}
