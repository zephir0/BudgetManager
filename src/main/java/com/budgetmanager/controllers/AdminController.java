package com.budgetmanager.controllers;

import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.User;
import com.budgetmanager.exceptions.NotAuthorizedException;
import com.budgetmanager.services.BudgetService;
import com.budgetmanager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/panel")
public class AdminController {
    private final BudgetService budgetService;
    private final UserService userService;

    public AdminController(BudgetService budgetService,
                           UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping("/budget/findAll/{userId}/")
    List<Budget> showAllBudgetByUserId(@PathVariable("userId") Long id) {
        return budgetService.showAllBudgetByUserId(id);
    }

    @GetMapping("/user/find/{id}")
    public ResponseEntity<Optional<User>> findUserById(@PathVariable("id") Long id) {
        try {
            return ResponseEntity.ok(userService.findUserById(id));
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @GetMapping("/user/findAll")
    public ResponseEntity<List<User>> findAllUsers() {
        try {
            return ResponseEntity.ok(userService.findAllUsers());
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }


}
