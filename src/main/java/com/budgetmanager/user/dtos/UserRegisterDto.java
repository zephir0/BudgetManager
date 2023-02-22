package com.budgetmanager.user.dtos;

public class UserRegisterDto {
    private final String login;
    private final String password;

    public UserRegisterDto(String login,
                           String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}



