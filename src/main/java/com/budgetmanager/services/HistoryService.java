package com.budgetmanager.services;

import com.budgetmanager.entities.History;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.HistoryRepository;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserService userService;

    public HistoryService(HistoryRepository historyRepository,
                          UserService userService) {
        this.historyRepository = historyRepository;
        this.userService = userService;
    }

    public int getHighestHistoryNumber() {
        User user = userService.getLoggedUser().get();
        return historyRepository.highestBudgetDayNumber(user.getId());
    }

    public History createFirstHistoryDay() {
        History history = new History();
        history.setUser(userService.getLoggedUser().get());
        history.setBudgetDayNumber(1);
        historyRepository.save(history);
        return history;
    }

    public void createNextDay() {
        User user = userService.getLoggedUser().get();
        int dayNumber = historyRepository.highestBudgetDayNumber(user.getId());
        History history = new History();
        history.setUser(user);
        history.setBudgetDayNumber(dayNumber + 1);
        historyRepository.save(history);
    }

    public boolean isHistoryEmpty() {
        Long loggedUserId = userService.getLoggedUserId();
        return historyRepository.findByBudgetDayNumberAndUserId(getHighestHistoryNumber(), loggedUserId).isEmpty();
    }
}
