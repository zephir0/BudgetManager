package com.budgetmanager.user.mappers;

import com.budgetmanager.user.dtos.UserRegisterDto;
import com.budgetmanager.user.entities.User;
import com.budgetmanager.user.entities.UserRoles;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserRegistrationMapper {
    public static User map(UserRegisterDto userRegisterDto,
                           PasswordEncoder passwordEncoder) {
        User user = new User();
        user.setLogin(userRegisterDto.getLogin());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setRole(UserRoles.USER);
        return user;
    }
}
