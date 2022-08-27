package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.services.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestController {
    private final BudgetService budgetService;

    public TestController(BudgetService budgetService) {
        this.budgetService = budgetService;
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


}
