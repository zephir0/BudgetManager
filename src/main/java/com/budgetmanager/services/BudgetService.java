package com.budgetmanager.services;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.History;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.BudgetRepository;
import com.budgetmanager.repositories.HistoryRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final HistoryRepository historyRepository;
    private final HistoryService historyService;

    public BudgetService(BudgetRepository budgetRepository,
                         UserService userService,
                         HistoryRepository historyRepository,
                         HistoryService historyService) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
        this.historyRepository = historyRepository;
        this.historyService = historyService;
    }


    public void addBudget(BudgetDto budgetdto) {
        if (historyService.isHistoryEmpty()) {
            historyService.createFirstHistoryDay();
        }

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
        budget.setUser(userService
                .getLoggedUser()
                .get());
        budget.setExpense(budgetDto.getExpense());
        budget.setIncome(budgetDto.getIncome());
        return budget;
    }

    public int findHighestDayNumber() {
        return historyRepository.highestBudgetDayNumber(getLoggedUserId());
    }

    public void increaseDayNumber() {
        History history = new History();
        int dayNumber = findHighestDayNumber() + 1;
        history.setBudgetDayNumber(dayNumber);
        historyRepository.save(history);
    }

    public Long getLoggedUserId() {
        Optional<User> user = userService.getLoggedUser();
        if (user.isEmpty()) {
            throw new RuntimeException("User not found / Not authorized");
        }
        return user.get().getId();
    }

    public int countAllIncomes() {
        int allIncomes = 0;
        for (Budget budget : activeUser()) {
            allIncomes += budget.getIncome();
        }
        return allIncomes;
    }

    public int countAllExpenses() {
        int allExpenses = 0;
        for (Budget budget : activeUser()) {
            allExpenses += budget.getExpense();
        }
        return allExpenses;
    }

    public int countAllBudgetValue() {
        return countAllIncomes() - countAllExpenses();
    }

    Iterable<Budget> activeUser() {
        return budgetRepository.findAllByUserId(getLoggedUserId());
    }

    //TO BE REMOVED OR CHANGED
    public List<List<Integer>> budgetList() {
        List<List<Integer>> allList = new ArrayList<>();
        List<Integer> expenseList = new ArrayList<>();
        List<Integer> incomeList = new ArrayList<>();

        for (Budget budget : activeUser()) {
            expenseList.add(budget.getExpense());
            incomeList.add(budget.getIncome());
        }

        allList.add(expenseList);
        allList.add(incomeList);
        return allList;
    }


}
