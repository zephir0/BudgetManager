package com.budgetmanager.configurations;

import com.budgetmanager.user.entities.UserRoles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain configuration(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests((auth) -> auth
                        .antMatchers("/api/admin**")
                        .hasRole("ADMIN")
                        .antMatchers("/auth/api**")
                        .permitAll()
                );
        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws
            Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


}
