package com.budgetmanager.Auth;

import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.dtos.UserRegisterDto;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.UserAlreadyExistException;
import com.budgetmanager.user.services.RegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegisterServiceTests {
    @Autowired
    private RegistrationService registrationService;
    @MockBean
    private UserRepository userRepository;


    private User user;
    private UserRegisterDto userRegisterDto;

    @BeforeEach
    void setUp() {
        userRegisterDto = new UserRegisterDto("admin", "admin");
        user = new User();
        user.setId(1L);
        user.setLogin("admin");
        user.setPassword("admin");
        user.setRole(UserRoles.ADMIN);
    }

    @Test
    public void shouldThrowUserAlreadyExistExceptionWhenUserExist() {
        when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);
        assertThrows(UserAlreadyExistException.class, () -> registrationService.registerUser(userRegisterDto));
        verify(userRepository).existsByLogin(user.getLogin());
    }

    @Test
    public void shouldReturnTrueIfUserExist() {
        when(userRepository.existsByLogin(user.getLogin())).thenReturn(true);
        assertTrue(registrationService.checkIfUserExist(user.getLogin()));
    }


}
