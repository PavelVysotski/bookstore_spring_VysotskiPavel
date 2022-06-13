package com.belhard.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .usersByUsernameQuery("select name, password, activity from users where name = ?")
                .authoritiesByUsernameQuery("select name, role from users where name = ?");
    }

    //    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        UserBuilder userBuilder = User.withDefaultPasswordEncoder();
//
//        auth.inMemoryAuthentication()
//                .withUser(userBuilder.username("admin")
//                        .password("admin")
//                        .roles("ADMIN"))
//                .withUser(userBuilder.username("manager")
//                        .password("manager")
//                        .roles("MANAGER"))
//                .withUser(userBuilder.username("customer")
//                        .password("customer")
//                        .roles("CUSTOMER"));
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                    .antMatchers("/api/**").permitAll()
//                    .antMatchers("/api/books/count").hasAnyRole("ADMIN", "MANAGER")
//                    .antMatchers("/api/updateBook/**").hasAnyRole("ADMIN", "MANAGER")
//                    .antMatchers("/api/books/delete/**").hasAnyRole("ADMIN", "MANAGER")
//                    .antMatchers("/api/orders/delete/**").hasAnyRole("ADMIN", "MANAGER")
//                    .antMatchers("/api/users/delete/**").hasRole("ADMIN")
//                    .antMatchers("/api/users").hasAnyRole("ADMIN", "MANAGER")
                .and()
                    .formLogin()
                    .permitAll()
                .and()
                    .logout()
                    .permitAll()
                .and()
                    .csrf()
                    .disable();
    }
}
