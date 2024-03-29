package com.budgetmanager.user.mappers;

import com.budgetmanager.user.dtos.UserLoginDto;
import com.budgetmanager.user.entities.User;

public class UserLoginDtoMapper {
    public static UserLoginDto map(User user) {
        String login = user.getLogin();
        String password = user.getPassword();
        return new UserLoginDto(login, password);
    }
}
