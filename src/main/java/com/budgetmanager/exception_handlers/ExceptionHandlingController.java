package com.budgetmanager.exception_handlers;

import com.budgetmanager.budget.exceptions.BudgetDoesntExistException;
import com.budgetmanager.budget.exceptions.BudgetValueNullException;
import com.budgetmanager.budget.exceptions.EmptyBudgetCategoryException;
import com.budgetmanager.budget.exceptions.EmptyBudgetTypeException;
import com.budgetmanager.ticket.exceptions.TicketDoesntExistException;
import com.budgetmanager.user.exceptions.NotAuthorizedException;
import com.budgetmanager.user.exceptions.UserAlreadyExistException;
import com.budgetmanager.user.exceptions.UserDoesntExistException;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<String> handleUserNotLoggedInException(UserNotLoggedInException ex) {
        return new ResponseEntity<>("User is not logged in.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BudgetValueNullException.class)
    public ResponseEntity<String> handleBudgetValueNullException() {
        return new ResponseEntity<>("You need to insert a value.", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmptyBudgetTypeException.class)
    public ResponseEntity<String> handleEmptyBudgetTypeException() {
        return new ResponseEntity<>("You need to insert a budget type.", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(EmptyBudgetCategoryException.class)
    public ResponseEntity<String> handleEmptyBudgetCategoryException() {
        return new ResponseEntity<>("You need to insert an income or expense category.", HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(BudgetDoesntExistException.class)
    public ResponseEntity<String> handleBudgetDoesntExistException() {
        return new ResponseEntity<>("Budget doesn't exist.", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<String> handleNotAuthorizedException() {
        return new ResponseEntity<>("You are not a budget creator or admin.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException() {
        return new ResponseEntity<>("Authentication failed.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<String> handleUserAlreadyExistException() {
        return new ResponseEntity<>("User already exist in database.", HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TicketDoesntExistException.class)
    public ResponseEntity<String> handleTicketDoesntExistException() {
        return new ResponseEntity<>("Ticket doesn't exist.", HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(UserDoesntExistException.class)
    public ResponseEntity<String> handleUserDoesntExistException() {
        return new ResponseEntity<>("User doesn't exist.", HttpStatus.NOT_FOUND);
    }

}
