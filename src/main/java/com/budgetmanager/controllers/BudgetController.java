package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.ExpenseCategory;
import com.budgetmanager.entities.IncomeCategory;
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
        System.out.println("USER ID : " + userService.getLoggedUser().getId());
        budgetService.deleteAllBudgetsByUserId(userService.getLoggedUser().getId());
        return new ResponseEntity<>("All budgets deleted", HttpStatus.OK);
    }

    @GetMapping("/EXPENSE/findAll/{expenseCategory}")
    List<Budget> findAllByExpenseCategory(@PathVariable("expenseCategory") ExpenseCategory expenseCategory) {
        return budgetService.findAllBudgetsByExpenseCategory(expenseCategory);
    }

    @GetMapping("/INCOME/findAll/{incomeCategory}")
    List<Budget> findAllByIncomeCategory(@PathVariable("incomeCategory") IncomeCategory incomeCategory) {
        return budgetService.findAllBudgetsByIncomeCategory(incomeCategory);
    }


    @GetMapping("/findAll")
    List<Budget> showLoggedUserAllBudget() {
        return budgetService.showAllBudget();
    }

    @GetMapping("/findAll/{dayNumber}")
    List<Budget> findBudgetByHistoryDayNumberAndUserId(@PathVariable("dayNumber") String day) {
        return budgetService.showBudgetByHistoryDayNumberAndUserId(day, userService.getLoggedUser().getId());
    }

    @GetMapping("/count/total")
    int countBudgetValue() {
        return budgetService.countAllBudgetValue();
    }
}
