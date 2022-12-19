package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.UserLoginDto;
import com.budgetmanager.DTOs.UserRegisterDto;
import com.budgetmanager.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        registrationService.registerUser(userRegisterDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }


}