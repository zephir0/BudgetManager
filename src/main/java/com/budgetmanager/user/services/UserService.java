package com.budgetmanager.user.services;

import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.dtos.UserLoginChangeDto;
import com.budgetmanager.user.dtos.UserLoginDto;
import com.budgetmanager.user.dtos.UserPasswordChangeDto;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.exceptions.IncorrectOldPasswordException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
import com.budgetmanager.user.mappers.UserLoginDtoMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public void changePassword(UserPasswordChangeDto userPasswordChangeDto) {
        User loggedUser = getLoggedUser();
        if (passwordEncoder.matches(userPasswordChangeDto.getOldPassword(), loggedUser.getPassword())) {
            String newPass = userPasswordChangeDto.getNewPassword();
            loggedUser.setPassword(passwordEncoder.encode(newPass));
            userRepository.save(loggedUser);
        } else throw new IncorrectOldPasswordException("Incorrect old password");
    }

    public void changeLogin(UserLoginChangeDto userLoginChangeDto) {
        User loggedUser = getLoggedUser();
        if (passwordEncoder.matches(userLoginChangeDto.getUserPassword(), loggedUser.getPassword())) {
            loggedUser.setLogin(userLoginChangeDto.getNewLogin());
            userRepository.save(loggedUser);
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
}
