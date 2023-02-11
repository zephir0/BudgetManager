package com.budgetmanager.Auth;

import com.budgetmanager.DTOs.UserRegisterMapper;
import com.budgetmanager.entities.User;
import com.budgetmanager.entities.UserRoles;
import com.budgetmanager.exceptions.UserAlreadyExistException;
import com.budgetmanager.repositories.UserRepository;
import com.budgetmanager.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegisterServiceTests {
    @Autowired
    private RegistrationService registrationService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private RoleRepository roleRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setLogin("admin");
        user.setPassword("admin");

        user.setRole(UserRoles.ADMIN);
    }

    @Test
    public void shouldThrowUserAlreadyExistExceptionWhenUserExist() {
        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        assertThrows(UserAlreadyExistException.class, () -> registrationService.registerUser(UserRegisterMapper.mapper(user)));
        verify(userRepository).findByLogin(user.getLogin());
    }

    @Test
    public void shouldReturnTrueIfUserExist() {
        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        assertTrue(registrationService.checkIfUserExist(user.getLogin()));
    }

    @Test
    public void shouldAddRoleToUser() {
        when(UserRoles.ADMIN);
        registrationService.addRoleToUser(user);
        assertEquals("ADMIN", user.getRole().name());
    }
}