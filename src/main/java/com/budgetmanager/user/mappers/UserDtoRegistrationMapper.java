package com.budgetmanager.user.mappers;

import com.budgetmanager.user.dtos.UserRegisterDto;
import com.budgetmanager.user.entities.User;

public class UserDtoRegistrationMapper {
    public static UserRegisterDto mapper(User user) {
        return new UserRegisterDto(user.getLogin(), user.getPassword());
    }
}

