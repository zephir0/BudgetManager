package com.budgetmanager.user.controllers;

import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.user.exceptions.UserDoesntExistException;
import com.budgetmanager.user.services.UserService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.budget.services.BudgetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin/panel")
@Tag(name = "Admin", description = "Endpoints for managing budgets and users")
public class AdminController {
    private final BudgetService budgetService;
    private final UserService userService;

    public AdminController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @GetMapping("/budget/findAll/{userId}")
    @Operation(summary = "Show all budgets by user ID", description = "Returns a list of all budgets for a specified user ID")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, name = "userId", description = "User ID", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of budgets"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public List<Budget> showAllBudgetByUserId(@PathVariable("userId") Long id) {
        return budgetService.showAllBudgetByUserId(id);
    }

    @GetMapping("/user/find/{id}")
    @Operation(summary = "Find a user by ID", description = "Returns a user with the specified ID")
    @Parameters({
            @Parameter(in = ParameterIn.PATH, name = "id", description = "User ID", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Optional<User>> findUserById(@PathVariable("id") Long id) throws NotAuthorizedException, UserDoesntExistException {
        return ResponseEntity.ok(userService.findUserById(id));
    }

    @GetMapping("/user/findAll")
    @Operation(summary = "Show all users", description = "Returns a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<List<User>> findAllUsers() throws NotAuthorizedException {
        return ResponseEntity.ok(userService.findAllUsers());
    }
}