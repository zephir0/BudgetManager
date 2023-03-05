package com.budgetmanager.budget.controllers;

import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
import com.budgetmanager.budget.services.BudgetService;
import com.budgetmanager.user.services.UserService;
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

import java.util.List;

@RestController
@RequestMapping("/api/budget")
@Tag(name = "Budgets", description = "Endpoints for managing user budgets")
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService,
                            UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Add new budget", description = "Adds a new budget for the logged in user")
    @Parameters({@Parameter(in = ParameterIn.DEFAULT, name = "budgetDto", description = "New budget", required = true)})
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Budget item added"), @ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "406", description = "Wrong budget category or wrong budget type"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<String> addBudget(@RequestBody BudgetDto budgetDto) {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>("Budget item added", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Change budget", description = "Modifies the selected budget")
    @Parameters({@Parameter(in = ParameterIn.PATH, name = "id", description = "Budget ID", required = true), @Parameter(in = ParameterIn.DEFAULT, name = "budgetDto", description = "Modified budget", required = true)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Budget changed"), @ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Budget not found"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<String> changeBudget(@RequestBody BudgetDto budgetDto,
                                               @PathVariable Long id) {
        budgetService.changeBudget(id, budgetDto);
        return new ResponseEntity<>("Budget changed", HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete budget", description = "Deletes the selected budget")
    @Parameters({@Parameter(in = ParameterIn.PATH, name = "id", description = "Budget ID", required = true),})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Budget deleted"), @ApiResponse(responseCode = "400", description = "Bad request"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Budget not found"), @ApiResponse(responseCode = "500", description = "Internal server error")})

    public ResponseEntity<String> deleteBudget(@PathVariable Long id) {
        budgetService.deleteByBudgetId(id);
        return new ResponseEntity<>("Budget deleted", HttpStatus.OK);
    }


    @DeleteMapping("/deleteAll")
    @Operation(summary = "Delete all budgets", description = "Deletes all budgets for the logged in user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "All budgets deleted"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<String> deleteAllBudgets() {
        budgetService.deleteAllBudgetsByUserId(userService.getLoggedUser().getId());
        return new ResponseEntity<>("All budgets deleted", HttpStatus.OK);
    }


    @GetMapping("{budgetType}/findAll/{category}")
    @Operation(summary = "Find budgets by expense category", description = "Returns a list of all budgets for the logged in user with the specified expense category")
    @Parameters({@Parameter(in = ParameterIn.PATH, name = "expenseCategory", description = "Expense category", required = true)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of budgets"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Budget category not found"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    public List<Budget> findAllByCategory(@PathVariable BudgetType budgetType,
                                          @PathVariable String category) {
        if (budgetType == BudgetType.EXPENSE)
            return budgetService.findAllBudgetsByCategoryForUser(ExpenseCategory.valueOf(category));
        else if (budgetType == BudgetType.INCOME)
            return budgetService.findAllBudgetsByCategoryForUser(IncomeCategory.valueOf(category));
        else return null;
    }


    @GetMapping("/findAll")
    @Operation(summary = "Show all budgets", description = "Returns a list of all budgets for the logged in user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of budgets"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    public ResponseEntity<List<Budget>> showLoggedUserAllBudget() {
        return ResponseEntity.ok(budgetService.findAllBudgetsForLoggedUser());
    }

    @GetMapping("/findAll/{date}")
    @Operation(summary = "Find budgets by historical date", description = "Returns a list of all budgets for the logged in user with the specified date")
    @Parameters({@Parameter(in = ParameterIn.PATH, name = "date", description = "Historical date", required = true)})
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "List of budgets"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Budget not found"), @ApiResponse(responseCode = "500", description = "Internal server error")})
    public List<Budget> findBudgetByHistoryDayNumberAndUserId(@PathVariable String date) {
        return budgetService.findBudgetByHistoryDayNumberAndUserId(date, userService.getLoggedUser().getId());
    }


}