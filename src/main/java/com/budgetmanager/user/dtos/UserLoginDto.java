package com.budgetmanager.user.dtos;

public class UserLoginDto {
    private final String login;
    private final String password;

    public UserLoginDto(String login,
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
