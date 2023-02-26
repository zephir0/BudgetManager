package com.budgetmanager.user.dtos;

public class UserLoginChangeDto {
    private final String newLogin;
    private final String userPassword;

    public UserLoginChangeDto(String newLogin,
                              String userPassword) {
        this.newLogin = newLogin;
        this.userPassword = userPassword;
    }

    public String getNewLogin() {
        return newLogin;
    }

    public String getUserPassword() {
        return userPassword;
    }
}
