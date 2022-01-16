package com.kanrisoft.kanri.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Order(1)
@TestConfiguration
@EnableWebSecurity
@EnableWebMvc
public class TestSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // Disable CSRF
        httpSecurity.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/user/register").permitAll()
                .antMatchers("/api/v1/user/activate").permitAll()
                .antMatchers("/api/v1/login").permitAll()   // Permit all requests without authentication
                .anyRequest().permitAll();
    }
}
