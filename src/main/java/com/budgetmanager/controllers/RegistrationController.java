package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.UserRegisterDto;
import com.budgetmanager.services.RegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/api")
public class RegistrationController {
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    ResponseEntity<String> registerUser(@RequestBody UserRegisterDto userRegisterDto) {
        registrationService.registerUser(userRegisterDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
