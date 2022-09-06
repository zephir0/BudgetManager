package com.budgetmanager.controllers;

import com.budgetmanager.DTOs.CalcDto;
import com.budgetmanager.services.CalcService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/calc")
public class CalcController {
    private final CalcService calcService;

    public CalcController(CalcService calcService) {
        this.calcService = calcService;
    }

    @PostMapping("/yearlybudget")
    ResponseEntity<String> calculateYearlyBudget(@RequestBody CalcDto calcDto) {
        String s = calcService.yearlyBudget(calcDto);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }
}
