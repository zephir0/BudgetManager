package com.budgetmanager.budget.controllers;

import com.budgetmanager.budget.exceptions.BudgetDoesntExistException;
import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.exceptions.EmptyBudgetCategoryException;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.entities.IncomeCategory;
import com.budgetmanager.budget.services.BudgetService;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
import com.budgetmanager.user.services.UserService;
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
                                     BudgetDto budgetDto) throws UserNotLoggedInException, NullPointerException, EmptyBudgetCategoryException {
        budgetService.addBudget(budgetDto);
        return new ResponseEntity<>("Budget item added", HttpStatus.CREATED);
    }


    @PutMapping("{id}")
    ResponseEntity<String> changeBudget(@RequestBody BudgetDto budgetDto,
                                        @PathVariable("id") Long id) throws BudgetDoesntExistException, UserNotLoggedInException, NotAuthorizedException {
        budgetService.changeBudget(id, budgetDto);
        return new ResponseEntity<>("Budget changed", HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteBudget(@PathVariable("id") Long id) throws
            UserNotLoggedInException, NotAuthorizedException, BudgetDoesntExistException {
        budgetService.deleteByBudgetId(id);
        return new ResponseEntity<>("Budget deleted", HttpStatus.OK);

    }

    @DeleteMapping("/deleteAll")
    ResponseEntity<String> deleteAllBudgets() throws UserNotLoggedInException {
        budgetService.deleteAllBudgetsByUserId(userService.getLoggedUser().getId());
        return new ResponseEntity<>("All budgets deleted", HttpStatus.OK);


    }

    @GetMapping("/expense/findAll/{expenseCategory}")
    List<Budget> findAllByExpenseCategory(@PathVariable("expenseCategory") ExpenseCategory expenseCategory) throws UserNotLoggedInException {
        return budgetService.findAllBudgetsByExpenseCategory(expenseCategory);
    }

    @GetMapping("/income/findAll/{incomeCategory}")
    List<Budget> findAllByIncomeCategory(@PathVariable("incomeCategory") IncomeCategory incomeCategory) throws UserNotLoggedInException {
        return budgetService.findAllBudgetsByIncomeCategory(incomeCategory);
    }


    @GetMapping("/findAll")
    ResponseEntity<List<Budget>> showLoggedUserAllBudget() throws UserNotLoggedInException {
        return ResponseEntity.ok(budgetService.showAllBudget());
    }

    @GetMapping("/findAll/{dayNumber}")
    List<Budget> findBudgetByHistoryDayNumberAndUserId(@PathVariable("dayNumber") String day) throws UserNotLoggedInException {
        return budgetService.showBudgetByHistoryDayNumberAndUserId(day, userService.getLoggedUser().getId());
    }

    @GetMapping("/count/total")
    int countBudgetValue() throws UserNotLoggedInException {
        return budgetService.countAllBudgetValue();
    }
}
