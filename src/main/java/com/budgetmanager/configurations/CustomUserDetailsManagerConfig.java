package com.budgetmanager.configurations;

import com.budgetmanager.DTOs.UserLoginDto;
import com.budgetmanager.entities.UserRoles;
import com.budgetmanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CustomUserDetailsManagerConfig implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public CustomUserDetailsManagerConfig(UserService userService) {
        this.userService = userService;
    }

    //SORT THIS OUT
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return userService.findByCredentialsLogin(login)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with login %s not found", login)));

    }

    private UserDetails createUserDetails(UserLoginDto userLoginDto) {

        return User.builder()
                .username(userLoginDto.getLogin())
                .password(userLoginDto.getPassword())
                .roles(UserRoles.USER.name(), UserRoles.ADMIN.name())
                .build();
    }


}
