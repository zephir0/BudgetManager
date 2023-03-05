package com.budgetmanager.exception_handlers;

import com.budgetmanager.budget.controllers.BudgetController;
import com.budgetmanager.user.controllers.AuthorizationController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice(basePackageClasses = {BudgetController.class, AuthorizationController.class})
public class CustomExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException() {
        return new ResponseEntity<>("Authentication failed.", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        if (Objects.requireNonNull(e.getMessage()).contains("IncomeCategory")) {
            return new ResponseEntity<>("Incorrect income category value.", HttpStatus.BAD_REQUEST);
        } else if (Objects.requireNonNull(e.getMessage()).contains("ExpenseCategory")) {
            return new ResponseEntity<>("Incorrect expense category value.", HttpStatus.BAD_REQUEST);
        } else if (Objects.requireNonNull(e.getMessage()).contains("BudgetType")) {
            return new ResponseEntity<>("Incorrect budget type.", HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
