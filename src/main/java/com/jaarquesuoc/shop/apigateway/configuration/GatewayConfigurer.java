package com.jaarquesuoc.shop.apigateway.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class GatewayConfigurer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/**/health").permitAll()
            .antMatchers("/**/products").permitAll()
            .antMatchers("/auth-ws/**").permitAll()
            .antMatchers("/**").authenticated();
    }
}
