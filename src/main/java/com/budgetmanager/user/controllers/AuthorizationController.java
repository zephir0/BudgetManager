package com.budgetmanager.user.controllers;

import com.budgetmanager.user.dtos.UserLoginDto;
import com.budgetmanager.user.dtos.UserRegisterDto;
import com.budgetmanager.user.exceptions.UserAlreadyExistException;
import com.budgetmanager.user.services.RegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication and registration")
public class AuthorizationController {
    private final AuthenticationManager authenticationManager;
    private final RegistrationService registrationService;

    public AuthorizationController(AuthenticationManager authenticationManager,
                                   RegistrationService registrationService) {
        this.authenticationManager = authenticationManager;
        this.registrationService = registrationService;
    }

    @PostMapping("/login")
    @Operation(summary = "User login", description = "Authenticates a user by username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<String> postLogin(@RequestBody UserLoginDto loginDto) throws AuthenticationException {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getLogin(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("Username signed successfully", HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "User registration", description = "Registers a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Registration successful"),
            @ApiResponse(responseCode = "409", description = "User already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    ResponseEntity<String> registerUser(@RequestBody UserRegisterDto userRegisterDto) throws UserAlreadyExistException {
        registrationService.registerUser(userRegisterDto);
        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("/logout")
    @Operation(summary = "User logout", description = "Logs out a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Logout successful"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
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

}

