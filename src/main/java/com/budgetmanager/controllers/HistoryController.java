package com.budgetmanager.controllers;

import com.budgetmanager.services.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/history/")
public class HistoryController {
    private final HistoryService historyService;

    public HistoryController(HistoryService historyService) {
        this.historyService = historyService;
    }

    @PostMapping("/create")
    ResponseEntity<String> createNextHistoryDay() {
        historyService.createNextDay();
        return new ResponseEntity<>("Next day created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/highestnumber")
    int getLoggedUserHighestHistoryDay() {
        return historyService.getHighestHistoryNumber();
    }

}
