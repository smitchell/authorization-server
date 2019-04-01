package com.example.service.auth.config;

import com.example.service.auth.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * For configuring the end users recognized by this Authorization Server
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final AuthUserDetailsService authUserDetailsService;

  @Autowired
  public WebSecurityConfig(AuthUserDetailsService authUserDetailsService) {
    this.authUserDetailsService = authUserDetailsService;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .userDetailsService(authUserDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

}