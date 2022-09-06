package com.budgetmanager.User;

import com.budgetmanager.entities.User;
import com.budgetmanager.repositories.UserRepository;
import com.budgetmanager.services.RegistrationService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;


    @Test
    @Order(1)
    void addUserToDatabase() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testUser");
        userRepository.save(user);
        String username = userRepository.findByLogin("testUser").get().getLogin();
        assertEquals("testUser", username);
    }

    @Test
    @Order(2)
    public void readAllUsers() {
        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers.size()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    public void findUserByLogin() {
        assertEquals("admin", userRepository.findByLogin("admin").get().getLogin());
    }

    @Test()
    @Order(4)
    void findUserByLoginAndDelete() {
        User user = userRepository.findByLogin("Administrator3").get();
        userRepository.delete(user);
        assertFalse(userRepository.existsByLogin(user.getLogin()));
    }

    @Test
    @Order(5)
    void shouldThrowExceptionAfterUserDelete() {
        assertThrows(NoSuchElementException.class,
                () -> userRepository.delete
                        (userRepository.findByLogin("testUser")
                                .get()));
    }


}
