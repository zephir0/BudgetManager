package com.budgetmanager.services;

import com.budgetmanager.entities.History;
import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.HistoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return historyRepository.highestBudgetDayNumber(userService.getLoggedUserId());
    }

    public void createFirstHistoryDay() {
        History history = new History();
        history.setUser(userService.getLoggedUser().get());
        history.setBudgetDayNumber(1);
        historyRepository.save(history);
    }

    public int getHistoryDayNumber() {
        User user = userService.getLoggedUser().get();
        Optional<History> history = historyRepository.findByBudgetDayNumberAndUserId(getHighestHistoryNumber(), user.getId());
        return history.get().getBudgetDayNumber();
    }

    public List<History> findAllHistoryByUserId(Long id) {
        return historyRepository.findAllByUserId(id);
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
