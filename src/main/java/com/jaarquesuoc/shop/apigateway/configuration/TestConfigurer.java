package com.jaarquesuoc.shop.apigateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Order(-20)
@Configuration
public class TestConfigurer extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .cors().and()
            .logout().logoutSuccessUrl("/")
            .and()
            .authorizeRequests().antMatchers("/**/health").permitAll()
            .and()
            .authorizeRequests().antMatchers("/**/products").permitAll()
            .anyRequest().authenticated()
            .and()
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }

}
