package com.budgetmanager.user.services;

import com.budgetmanager.user.dtos.UserLoginChangeDto;
import com.budgetmanager.user.dtos.UserPasswordChangeDto;
import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.dtos.UserLoginDto;
import com.budgetmanager.user.mappers.UserLoginDtoMapper;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.IncorrectOldPasswordException;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserLoginDto> findByCredentialsLogin(String login) {
        return userRepository.findByLogin(login).map(UserLoginDtoMapper::map);
    }

    public Optional<User> findUserById(Long id) {
        if (getLoggedUser().getRole().equals(UserRoles.ADMIN)) {
            return userRepository.findUserById(id);
        } else throw new NotAuthorizedException("You are not admin");
    }


    public void changePassword(UserPasswordChangeDto userPasswordChangeDto) {
        if (passwordEncoder.matches(userPasswordChangeDto.getOldPassword(), getLoggedUser().getPassword())) {
            String newPass = userPasswordChangeDto.getNewPassword();
            getLoggedUser().setPassword(passwordEncoder.encode(newPass));
            userRepository.save(getLoggedUser());
        } else throw new IncorrectOldPasswordException("Incorrect old password");

    }


    public void changeLogin(UserLoginChangeDto userLoginChangeDto) {
        if (passwordEncoder.matches(userLoginChangeDto.getUserPassword(), getLoggedUser().getPassword())) {
            getLoggedUser().setLogin(userLoginChangeDto.getNewLogin());
            userRepository.save(getLoggedUser());
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            authentication = new UsernamePasswordAuthenticationToken(userLoginChangeDto.getNewLogin(), authentication.getCredentials(), authentication.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } else throw new IncorrectOldPasswordException("Incorrect old password");

    }

    public User getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return Optional.of(authentication)
                .filter(Authentication::isAuthenticated)
                .map(Authentication::getName)
                .flatMap(userRepository::findByLogin)
                .orElseThrow(() -> new UserNotLoggedInException("User is not logged."));
    }

    public List<User> findAllUsers() {
        if (getLoggedUser().getRole().equals(UserRoles.ADMIN)) {
            return new ArrayList<>(userRepository.findAll());
        } else throw new NotAuthorizedException("You are not admin");
    }

}
