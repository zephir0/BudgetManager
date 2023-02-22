package com.budgetmanager.user.controllers;

import com.budgetmanager.user.dtos.UserLoginChangeDto;
import com.budgetmanager.user.dtos.UserPasswordChangeDto;
import com.budgetmanager.user.services.UserService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.exceptions.IncorrectLoginChangeException;
import com.budgetmanager.user.exceptions.IncorrectOldPasswordException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
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
                                          UserPasswordChangeDto userPasswordChangeDto) throws UserNotLoggedInException {
        try {
            userService.changePassword(userPasswordChangeDto);
            return new ResponseEntity<>("Password successfully changed to: " + userPasswordChangeDto.getNewPassword(), HttpStatus.OK);
        } catch (IncorrectOldPasswordException e) {
            return new ResponseEntity<>("Old password is not: " + userPasswordChangeDto.getOldPassword(), HttpStatus.UNAUTHORIZED);

        }
    }

    @PostMapping("/changeLogin")
    ResponseEntity<String> changeLogin(@RequestBody
                                       UserLoginChangeDto userLoginChangeDto) throws UserNotLoggedInException {
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