package com.budgetmanager.controllers;

import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.User;
import com.budgetmanager.services.BudgetService;
import com.budgetmanager.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
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
    public Optional<User> findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/user/findAll")
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }


}
