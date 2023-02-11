package com.budgetmanager.DTOs;

import com.budgetmanager.entities.User;

public class UserRegisterMapper {
    public static UserRegisterDto mapper(User user) {
        return new UserRegisterDto(user.getLogin(), user.getPassword());
    }
}
