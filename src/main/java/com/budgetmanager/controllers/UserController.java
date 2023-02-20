package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.UserLoginChangeDto;
import com.budgetmanager.DTOs.UserPasswordChangeDto;
import com.budgetmanager.entities.User;
import com.budgetmanager.exceptions.IncorrectLoginChangeException;
import com.budgetmanager.exceptions.IncorrectOldPasswordException;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/changePassword")
    ResponseEntity<String> changePassword(@RequestBody
                                          UserPasswordChangeDto userPasswordChangeDto) {
        try {
            userService.changePassword(userPasswordChangeDto);
            return new ResponseEntity<>("Password successfully changed to: " + userPasswordChangeDto.getNewPassword(), HttpStatus.OK);
        } catch (NotAuthorizedException e) {
            return new ResponseEntity<>("You are not logged in", HttpStatus.UNAUTHORIZED);

        } catch (IncorrectOldPasswordException e) {
            return new ResponseEntity<>("Old password is not: " + userPasswordChangeDto.getOldPassword(), HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("/changeLogin")
    ResponseEntity<String> changeLogin(@RequestBody
                                       UserLoginChangeDto userLoginChangeDto) {
        try {
            userService.changeLogin(userLoginChangeDto);
            return new ResponseEntity<>("Login successfully change to: " + userLoginChangeDto.getNewLogin(), HttpStatus.OK);
        } catch (IncorrectLoginChangeException e) {
            return new ResponseEntity<>("Cannot change login to: " + userLoginChangeDto.getNewLogin(), HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping()
    public User loggedUser() {
        return userService.getLoggedUser();
    }
}
