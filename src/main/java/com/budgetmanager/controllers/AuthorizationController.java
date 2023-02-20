package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.UserLoginDto;
import com.budgetmanager.DTOs.UserRegisterDto;
import com.budgetmanager.exceptions.UserAlreadyExistException;
import com.budgetmanager.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth/api")
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;

    public AuthorizationController(AuthenticationManager authenticationManager,
                                   RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    ResponseEntity<String> postLogin(@RequestBody UserLoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("Username signed successfully", HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Authentication failed", HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        try {
            registrationService.registerUser(userRegisterDto);
            return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
        } catch (UserAlreadyExistException e) {
            return new ResponseEntity<>("User already exist in database", HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/logout")
    ResponseEntity<String> logoutUser(HttpServletRequest request,
                                      HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return new ResponseEntity<>("You cannot log out a user who is not currently logged in", HttpStatus.UNAUTHORIZED);
        } else {
            SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
            securityContextLogoutHandler.logout(request, response, authentication);
            return new ResponseEntity<>("User logged out.", HttpStatus.OK);
        }

    }


}
