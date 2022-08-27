package com.budgetmanager.services;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;

    public BudgetService(BudgetRepository budgetRepository,
                         UserService userService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
    }

    public void addBudget(BudgetDto budgetdto) {
        Budget budget = mapper(budgetdto);
        budgetRepository.save(budget);
    }

    public List<Budget> showAllBudget() {
        List<Budget> budgetList = new ArrayList<>();
        Long userId = userService.getLoggedUser()
                .get()
                .getId();
        Iterable<Budget> allByUserId = budgetRepository.findAllByUserId(userId);
        allByUserId.forEach(budgetList::add);
        return budgetList;
    }

    public Budget mapper(BudgetDto budgetDto) {
        Budget budget = new Budget();
        budget.setExpense(budgetDto.getExpense());
        budget.setIncome(budgetDto.getIncome());
        budget.setUser(userService.getLoggedUser().get());
        return budget;
    }
}
