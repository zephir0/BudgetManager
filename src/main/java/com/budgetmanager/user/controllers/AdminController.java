package com.budgetmanager.user.controllers;

import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.user.services.UserService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.budget.services.BudgetService;
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
    public ResponseEntity<Optional<User>> findUserById(@PathVariable("id") Long id) throws NotAuthorizedException {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/user/findAll")
    public ResponseEntity<List<User>> findAllUsers() throws NotAuthorizedException {
        return ResponseEntity.ok(userService.findAllUsers());

    }


}
