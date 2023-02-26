package com.budgetmanager.user.dtos;

public class UserPasswordChangeDto {
    private final String oldPassword;
    private final String newPassword;

    public UserPasswordChangeDto(String oldPassword,
                                 String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
