package com.budgetmanager.DTOs;

import com.budgetmanager.entities.User;

public class UserLoginDtoMapper {
    public static UserLoginDto map(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        return new UserLoginDto(login, password);
    }
}
