package com.budgetmanager.services;

import com.budgetmanager.DTOs.BudgetDto;
import com.budgetmanager.entities.Budget;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.BudgetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
        Budget budget = BudgetMapper.map(budgetdto, userService.getLoggedUser().get());
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


    public void deleteByBudgetId(Long id) {
        budgetRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllBudgetsByUserId(Long id) {
        budgetRepository.deleteAllByUserId(id);
    }

    public Long getLoggedUserId() {
        User user = userService.getLoggedUser().orElseThrow(() -> new RuntimeException("User not found / Not authorized"));
        return user.getId();
    }

    public int count(String type) {
        int result = 0;
        for (Budget budget : allBudgetListForUserId()) {
            if ("incomes".equals(type)) {
                result += budget.getIncome();
            } else if ("expenses".equals(type)) {
                result += budget.getExpense();
            } else {
                throw new IllegalArgumentException("Invalid type: " + type);
            }
        }
        return result;
    }


    public int countAllBudgetValue() {
        return count("incomes") - count("expenses");
    }

    Iterable<Budget> allBudgetListForUserId() {
        return budgetRepository.findAllByUserId(getLoggedUserId());
    }

    public List<Budget> showAllBudgetByUserId(Long id) {
        List<Budget> budgetList = new ArrayList<>();
        budgetRepository.findAllByUserId(id).forEach(budgetList::add);
        return budgetList;
    }

    public List<Budget> showBudgetByHistoryDayNumberAndUserId(String day,
                                                              Long id) {
        return budgetRepository.findAllByHistoryDayNumberAndUserId(day, id);
    }

    static class BudgetMapper {
        static public Budget map(BudgetDto budgetDto,
                                 User user) {
            Budget budget = new Budget();
            budget.setUser(user);
            budget.setExpense(budgetDto.getExpense());
            budget.setIncome(budgetDto.getIncome());
            budget.setHistoryDayNumber(LocalDate.now().toString());
            return budget;
        }
    }
}
