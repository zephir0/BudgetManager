package com.budgetmanager.controllers;

import com.budgetmanager.entities.History;
import com.budgetmanager.services.HistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/find/{id}")
    List<History> findAllHistoryByUserId(@PathVariable("id") long id) {
        return historyService.findAllHistoryByUserId(id);
    }
}
