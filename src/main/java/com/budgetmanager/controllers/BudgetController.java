package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.services.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {
    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping("/add")
    ResponseEntity<String> addBudget(@RequestBody
                                     BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>("Budget item added", HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteBudget(@PathVariable("id") Long id) {
        budgetService.deleteByBudgetId(id);
        return new ResponseEntity<>("Budget item removed", HttpStatus.OK);
    }

    @GetMapping("/list")
    List<Budget> showLoggedUserAllBudget() {
        return budgetService.showAllBudget();
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

    @GetMapping("/find/userid/{id}")
    List<Budget> showAllBudgetByUserId(@PathVariable("id") Long id) {
        return budgetService.showAllBudgetByUserId(id);
    }

    @GetMapping("/find/history/{userid}/{day}")
    List<Budget> findBudgetByHistoryDayNumberAndUserId(@PathVariable("day") int day,
                                                       @PathVariable("userid") Long id) {
        return budgetService.showBudgetByHistoryDayNumberAndUserId(day, id);

    }


}
