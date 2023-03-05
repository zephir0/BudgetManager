package com.budgetmanager.Budget.controllers;

import com.budgetmanager.budget.dtos.BudgetDto;
import com.budgetmanager.budget.entities.BudgetType;
import com.budgetmanager.budget.entities.ExpenseCategory;
import com.budgetmanager.budget.exceptions.InvalidBudgetCategoryException;
import com.budgetmanager.budget.exceptions.InvalidBudgetTypeException;
import com.budgetmanager.budget.exceptions.InvalidBudgetValueException;
import com.budgetmanager.budget.services.BudgetService;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.exceptions.UserNotLoggedInException;
import com.budgetmanager.user.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BudgetControllerAddTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BudgetService budgetService;

    @MockBean
    private UserService userService;

    @Test
    void testAddBudgetValid() throws Exception {
        when(userService.getLoggedUser()).thenReturn(new User());
        doNothing().when(budgetService).addBudget(any(BudgetDto.class));

        BudgetDto budgetDto = new BudgetDto(100, BudgetType.EXPENSE, ExpenseCategory.FOOD, null);
        String json = objectMapper.writeValueAsString(budgetDto);
        MvcResult result = mockMvc.perform(post("/api/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        assertEquals("Budget item added", result.getResponse().getContentAsString());
        verify(budgetService).addBudget(any(BudgetDto.class));
    }


    @Test
    void shouldReturn401UserIsNotLoggedIn() throws Exception {
        when(userService.getLoggedUser()).thenReturn(null);

        doThrow(UserNotLoggedInException.class).when(budgetService).addBudget(any(BudgetDto.class));

        BudgetDto budgetDto = new BudgetDto(0, null, ExpenseCategory.FOOD, null);

        String json = objectMapper.writeValueAsString(budgetDto);
        MvcResult result = mockMvc.perform(post("/api/budget").contentType(MediaType.APPLICATION_JSON).content(
                json
        )).andExpect(status().is(401)).andReturn();
        assertEquals("User is not logged in.", result.getResponse().getErrorMessage());
    }

    @Test
    void shouldReturn406WhenBudgetTypeIsEmpty() throws Exception {
        when(userService.getLoggedUser()).thenReturn(new User());

        doThrow(InvalidBudgetTypeException.class).when(budgetService).addBudget(any(BudgetDto.class));

        BudgetDto budgetDto = new BudgetDto(0, null, ExpenseCategory.FOOD, null);

        String json = objectMapper.writeValueAsString(budgetDto);
        MvcResult result = mockMvc.perform(post("/api/budget").contentType(MediaType.APPLICATION_JSON).content(
                json
        )).andExpect(status().is(406)).andReturn();
        assertEquals("Invalid or missing budget type.", result.getResponse().getErrorMessage());
    }

    @Test
    void shouldReturn406WhenBudgetCategoryIsEmpty() throws Exception {
        when(userService.getLoggedUser()).thenReturn(new User());


        doThrow(InvalidBudgetCategoryException.class).when(budgetService).addBudget(any(BudgetDto.class));

        BudgetDto budgetDto = new BudgetDto(0, BudgetType.EXPENSE, null, null);

        String json = objectMapper.writeValueAsString(budgetDto);
        MvcResult result = mockMvc.perform(post("/api/budget").contentType(MediaType.APPLICATION_JSON).content(json)).andExpect(
                status().is(406)
        ).andReturn();

        assertEquals("Invalid or missing budget category.", result.getResponse().getErrorMessage());
    }

    @Test
    void shouldReturn406WhenBudgetValueIsZero() throws Exception {
        when(userService.getLoggedUser()).thenReturn(new User());


        doThrow(new InvalidBudgetValueException("Budget value should be higher than 0"))
                .when(budgetService).addBudget(any(BudgetDto.class));

        BudgetDto budgetDto = new BudgetDto(0, BudgetType.EXPENSE, ExpenseCategory.FOOD, null);

        String json = objectMapper.writeValueAsString(budgetDto);
        MvcResult result = mockMvc.perform(post("/api/budget")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is(406))
                .andReturn();

        assertEquals("You need to insert a value.", result.getResponse().getErrorMessage());
    }

}