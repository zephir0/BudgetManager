package com.budgetmanager.User;

import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.UserRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserRepositoryTests {
    @Autowired
    private UserRepository userRepository;


    @Test
    @Rollback(value = false)
    void addUserToDatabase() {
        User user = new User();
        user.setLogin("testUser");
        user.setPassword("testUser");
        userRepository.save(user);
        String username = userRepository.findByLogin("testUser").get().getLogin();
        assertEquals("testUser", username);
    }

    @Test
    public void readAllUsers() {
        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers.size()).isGreaterThan(0);
    }


    @Test
    public void findUserByLogin() {
        assertEquals("testUser", userRepository.findByLogin("testUser").get().getLogin());
    }

    @Test()
    @Rollback(value = false)
    void findUserByLoginAndDelete() {
        User user = userRepository.findByLogin("testUser").orElseThrow(() -> new IllegalArgumentException("User not found"));
        userRepository.delete(user);
        assertFalse(userRepository.existsByLogin(user.getLogin()));
    }


}
