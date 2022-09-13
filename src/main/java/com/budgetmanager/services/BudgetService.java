package com.budgetmanager.services;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.BudgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final HistoryService historyService;

    public BudgetService(BudgetRepository budgetRepository,
                         UserService userService,
                         HistoryService historyService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
        this.historyService = historyService;
    }


    public void addBudget(BudgetDto budgetdto) {
        if (historyService.isHistoryEmpty()) {
            historyService.createFirstHistoryDay();
        }
        Budget budget = mapper(budgetdto);
        budgetRepository.save(budget);
    }

    public void changeBudget(Long id,
                             BudgetDto budgetDto) {
        budgetRepository.findById(id).ifPresent(
                budget -> {
                    budget.setExpense(budgetDto.getExpense());
                    budget.setIncome(budgetDto.getIncome());
                    budgetRepository.save(budget);
                });
    }


    public List<Budget> showAllBudget() {
        return new ArrayList<>(budgetRepository.findAllByUserId(userService.getLoggedUserId()));
    }

    //TO BE CHANGED AFTER SORTING DAY NUMBER FROM HISTORIES
    public Budget mapper(BudgetDto budgetDto) {
        Budget budget = new Budget();
        budget.setUser(userService
                .getLoggedUser()
                .get());
        budget.setExpense(budgetDto.getExpense());
        budget.setIncome(budgetDto.getIncome());
        budget.setHistoryDayNumber(historyService.getHistoryDayNumber());
        return budget;
    }

    public User getBudgetCreator(Long id) {
        return budgetRepository.findById(id).get().getUser();
    }

    public void deleteByBudgetId(Long id) {
        budgetRepository.deleteById(id);
    }
    //INCREASE DAY NUMBER IN BUDGET TABLE - TO BO DONE

    public Long getLoggedUserId() {
        Optional<User> user = userService.getLoggedUser();
        if (user.isEmpty()) {
            throw new RuntimeException("User not found / Not authorized");
        }
        return user.get().getId();
    }

    public int countAllIncomes() {
        int allIncomes = 0;
        for (Budget budget : allBudgetListForUserId()) {
            allIncomes += budget.getIncome();
        }
        return allIncomes;
    }

    public int countAllExpenses() {
        int allExpenses = 0;
        for (Budget budget : allBudgetListForUserId()) {
            allExpenses += budget.getExpense();
        }
        return allExpenses;
    }

    public int countAllBudgetValue() {
        return countAllIncomes() - countAllExpenses();
    }

    Iterable<Budget> allBudgetListForUserId() {
        return budgetRepository.findAllByUserId(getLoggedUserId());
    }

    //TO BE REMOVED OR CHANGED
    public List<List<Integer>> budgetList() {
        List<List<Integer>> allList = new ArrayList<>();
        List<Integer> expenseList = new ArrayList<>();
        List<Integer> incomeList = new ArrayList<>();

        for (Budget budget : allBudgetListForUserId()) {
            expenseList.add(budget.getExpense());
            incomeList.add(budget.getIncome());
        }

        allList.add(expenseList);
        allList.add(incomeList);
        return allList;
    }

    public List<Budget> showAllBudgetByUserId(Long id) {
        List<Budget> budgetList = new ArrayList<>();
        budgetRepository.findAllByUserId(id).forEach(budgetList::add);
        return budgetList;
    }

    public List<Budget> showBudgetByHistoryDayNumberAndUserId(int day,
                                                              Long id) {
        return budgetRepository.findAllByHistoryDayNumberAndUserId(day, id);
    }

}
