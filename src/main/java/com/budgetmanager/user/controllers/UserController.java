package com.budgetmanager.user.controllers;

import com.budgetmanager.user.dtos.UserLoginChangeDto;
import com.budgetmanager.user.dtos.UserPasswordChangeDto;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Tag(name = "User Controller", description = "Operations related to user management")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/changePassword")
    @Operation(summary = "Change user's password", description = "Changes user's password to a new one.")
    @ApiResponse(responseCode = "200", description = "Password successfully changed.")
    @ApiResponse(responseCode = "401", description = "You are not logged in.")
    @ApiResponse(responseCode = "403", description = "Incorrect old password.")
    ResponseEntity<String> changePassword(@RequestBody UserPasswordChangeDto userPasswordChangeDto) {
        userService.changePassword(userPasswordChangeDto);
        return new ResponseEntity<>("Password successfully changed to: " + userPasswordChangeDto.getNewPassword(), HttpStatus.OK);

    }

    @PostMapping("/changeLogin")
    @Operation(summary = "Change user's login", description = "Changes user's login to a new one.")
    @ApiResponse(responseCode = "200", description = "Login successfully changed.")
    @ApiResponse(responseCode = "401", description = "You are not logged in.")
    @ApiResponse(responseCode = "406", description = "Cannot change login to specified value.")
    ResponseEntity<String> changeLogin(@RequestBody UserLoginChangeDto userLoginChangeDto) {
        userService.changeLogin(userLoginChangeDto);
        return new ResponseEntity<>("Login successfully change to: " + userLoginChangeDto.getNewLogin(), HttpStatus.OK);
    }

    @GetMapping()
    @Operation(summary = "Get logged in user", description = "Retrieves the currently logged in user.")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved logged in user.")
    @ApiResponse(responseCode = "401", description = "You are not logged in.")
    public User loggedUser() {
        return userService.getLoggedUser();
    }
}