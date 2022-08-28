package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.services.BudgetService;
import com.budgetmanager.services.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    private final BudgetService budgetService;
    private final HistoryService historyService;

    public TestController(BudgetService budgetService,
                          HistoryService historyService) {
        this.budgetService = budgetService;
        this.historyService = historyService;
    }

    @PostMapping("/addbudget")
    ResponseEntity<String> addBudget(@RequestBody
                                     BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>("Budget item added", HttpStatus.OK);
    }

    @GetMapping("/budgetlist")
    List<Budget> showBudgetList() {
        return budgetService.showAllBudget();
    }

    @GetMapping("/countincomes")
    int countAllIncomes() {
        return budgetService.countAllIncomes();
    }

    @GetMapping("/countexpenses")
    int countAllExpenses() {
        return budgetService.countAllExpenses();
    }

    @GetMapping("/budgetvalue")
    int countBudgetValue() {
        return budgetService.countAllBudgetValue();
    }


    @PostMapping("/createNextHistoryDay")
    ResponseEntity<String> createNextHistoryDay() {
        historyService.createNextDay();
        return new ResponseEntity<>("Next history day create", HttpStatus.OK);
    }

    @GetMapping("/getHighestHistoryDay")
    int getHighestHistoryDay() {
        return historyService.getHighestHistoryNumber();
    }


}
