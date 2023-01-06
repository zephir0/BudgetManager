package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.services.BudgetService;
import com.budgetmanager.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> addBudget(@RequestBody
                                     BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>("Budget item added", HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    ResponseEntity<String> changeBudget(@RequestBody BudgetDto budgetDto,
                                        @PathVariable("id") Long id) {
        budgetService.changeBudget(id, budgetDto);
        return new ResponseEntity<>("Budget changed", HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBudget(@PathVariable("id") Long id) {
        budgetService.deleteByBudgetId(id);
        return new ResponseEntity<>("Budget deleted", HttpStatus.OK);

    }

    @DeleteMapping()
    ResponseEntity<String> deleteAllBudgets() {
        System.out.println("USER ID : " + userService.getLoggedUserId());
        budgetService.deleteAllBudgetsByUserId(userService.getLoggedUserId());
        return new ResponseEntity<>("All budgets deleted", HttpStatus.OK);
    }

    @GetMapping()
    ResponseEntity<Long> printLoggedUserId(){
        Long loggedUserId = userService.getLoggedUserId();
        return new ResponseEntity<>(loggedUserId, HttpStatus.OK);
    }


    @GetMapping("/findAll")
    List<Budget> showLoggedUserAllBudget() {
        return budgetService.showAllBudget();
    }

    @GetMapping("/findAll/{dayNumber}")
    List<Budget> findBudgetByHistoryDayNumberAndUserId(@PathVariable("dayNumber") String day) {
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

    //TO BE RENAMED TO BALANCE
    @GetMapping("/count/total")
    int countBudgetValue() {
        return budgetService.countAllBudgetValue();
    }
}
