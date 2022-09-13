package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.services.BudgetService;
import com.budgetmanager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService,
                            UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @PostMapping()
    ResponseEntity<String> addBudget(@RequestBody
                                     BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>("Budget item added", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<String> changeBudget(@RequestBody BudgetDto budgetDto,
                                        @PathVariable("id") Long id) {
        if (userService.getLoggedUser().get().equals(budgetService.getBudgetCreator(id))) {
            budgetService.changeBudget(id, budgetDto);
            return new ResponseEntity<>("Budget changed", HttpStatus.OK);
        } else
            return new ResponseEntity<>("You are not a creator of that budget", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBudget(@PathVariable("id") Long id) {
        if (userService.getLoggedUser().get().equals(budgetService.getBudgetCreator(id))) {
            budgetService.deleteByBudgetId(id);
            return new ResponseEntity<>("Budget item removed", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("You are not a creator of that budget", HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/findAll")
    List<Budget> showLoggedUserAllBudget() {
        return budgetService.showAllBudget();
    }

    @GetMapping("/findAll/{dayNumber}")
    List<Budget> findBudgetByHistoryDayNumberAndUserId(@PathVariable("dayNumber") int day) {
        return budgetService.showBudgetByHistoryDayNumberAndUserId(day, userService.getLoggedUserId());
    }

    @GetMapping("/count/incomes")
    int countAllLoggedUserIncome() {
        return budgetService.countAllIncomes();
    }

    @GetMapping("/count/expenses")
    int countAllExpenses() {
        return budgetService.countAllExpenses();
    }

    @GetMapping("/count/total")
    int countBudgetValue() {
        return budgetService.countAllBudgetValue();
    }
}
