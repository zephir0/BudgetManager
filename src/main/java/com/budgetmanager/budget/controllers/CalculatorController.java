package com.budgetmanager.budget.controllers;

import com.budgetmanager.budget.dtos.CalcDto;
import com.budgetmanager.budget.services.CalculatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculate")
@Tag(name = "Calculator", description = "Endpoints for calculating budgets")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @PostMapping("/yearlybudget")
    @Operation(summary = "Calculate yearly budget", description = "Calculates the yearly budget based on the user's monthly income and expenses")
    @Parameters({
            @Parameter(in = ParameterIn.DEFAULT, name = "calcDto", description = "Calculation data", required = true)
    })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Yearly budget"),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<String> calculateYearlyBudget(@RequestBody CalcDto calcDto) {
        return new ResponseEntity<>(calculatorService.yearlyBudget(calcDto), HttpStatus.OK);
    }

    @GetMapping("/count/total")
    @Operation(summary = "Count total budget value", description = "Returns the total value of all budgets for the logged in user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total budget value"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public int countBudgetBalance() {
        return calculatorService.countBudgetBalance();
    }

}