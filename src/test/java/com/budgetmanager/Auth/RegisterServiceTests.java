package com.budgetmanager.Auth;

import com.budgetmanager.user.UserRepository;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import com.budgetmanager.user.exceptions.UserAlreadyExistException;
import com.budgetmanager.user.mappers.UserDtoRegistrationMapper;
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
        assertThrows(UserAlreadyExistException.class, () -> registrationService.registerUser(UserDtoRegistrationMapper.mapper(user)));
        verify(userRepository).findByLogin(user.getLogin());
    }

    @Test
    public void shouldReturnTrueIfUserExist() {
        when(userRepository.findByLogin(user.getLogin())).thenReturn(Optional.of(user));
        assertTrue(registrationService.checkIfUserExist(user.getLogin()));
    }

//    @Test
//    public void shouldAddRoleToUser() {
//        when(UserRoles.ADMIN);
//        registrationService.addRoleToUser(user);
//        assertEquals("ADMIN", user.getRole().name());
//    }
}
