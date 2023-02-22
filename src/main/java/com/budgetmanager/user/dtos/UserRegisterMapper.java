package com.budgetmanager.user.dtos;

import com.budgetmanager.user.entities.User;

public class UserRegisterMapper {
    public static UserRegisterDto mapper(User user) {
        return new UserRegisterDto(user.getLogin(), user.getPassword());
    }
}
