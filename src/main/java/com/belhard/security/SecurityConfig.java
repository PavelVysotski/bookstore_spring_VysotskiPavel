package com.belhard.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(userBuilder.username("pavel")
                        .password("pavel")
                        .roles("ADMIN"))
                .withUser(userBuilder.username("manager")
                        .password("manager")
                        .roles("MANAGER"))
                .withUser(userBuilder.username("customer")
                        .password("customer")
                        .roles("CUSTOMER"));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/books/count").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/updateBook/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/books/delete/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/orders/delete/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers("/users/delete/**").hasRole("ADMIN")
                .and().formLogin().permitAll();
    }
}
