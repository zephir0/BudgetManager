package com.budgetmanager.budget.services;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.dtos.CalcDto;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.user.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculatorService {

    private final UserService userService;
    private final BudgetRepository budgetRepository;

    public CalculatorService(UserService userService,
                             BudgetRepository budgetRepository) {
        this.userService = userService;
        this.budgetRepository = budgetRepository;
    }

    public String yearlyBudget(CalcDto calcDto) {
        return "Total =  " +
                calcDto.getIncomes()
                + " " + "+" + " " +
                calcDto.getExpenses()
                + " " + "=" +
                calculateYearlyBudget(calcDto.getIncomes(), calcDto.getExpenses());
    }

    public int calculateYearlyBudget(List<Integer> incomes,
                                     List<Integer> expenses) {
        int result = 0;
        for (Integer income : incomes) {
            result += income;
        }
        for (Integer expense : expenses) {
            result -= expense;
        }
        return result;
    }

    public int countBudgetBalance() {
        List<Budget> allBudgetsByUserId = budgetRepository.findAllByUserId(userService.getLoggedUser().getId());
        int incomesTotal = allBudgetsByUserId.stream().filter(budget -> budget.getBudgetType().equals(BudgetType.INCOME)).mapToInt(Budget::getValue).sum();
        int expensesTotal = allBudgetsByUserId.stream().filter(budget -> budget.getBudgetType().equals(BudgetType.EXPENSE)).mapToInt(Budget::getValue).sum();
        return incomesTotal - expensesTotal;
    }
}
