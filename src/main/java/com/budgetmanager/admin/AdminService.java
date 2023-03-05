package com.budgetmanager.admin;

import com.budgetmanager.budget.BudgetRepository;
import com.budgetmanager.budget.entities.Budget;
import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.exceptions.UserDoesntExistException;
import com.budgetmanager.user.services.UserService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {
    private final BudgetRepository budgetRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdminService(BudgetRepository budgetRepository,
                        UserService userService,
                        UserRepository userRepository) {
        this.budgetRepository = budgetRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public User findUserById(Long id) {
        if (userService.getLoggedUser().getRole().equals(UserRoles.ADMIN)) {
            return userRepository.findUserById(id).orElseThrow(() -> new UserDoesntExistException("User doesn't exist"));
        } else throw new NotAuthorizedException("You are not admin");
    }

    public List<User> findAllUsers() {
        if (userService.getLoggedUser().getRole().equals(UserRoles.ADMIN)) {
            return new ArrayList<>(userRepository.findAll());
        } else throw new NotAuthorizedException("You are not admin");
    }

    public List<Budget> findAllBudgetsByUserId(Long id) {
        return new ArrayList<>(budgetRepository.findAllByUserId(id));
    }
}
