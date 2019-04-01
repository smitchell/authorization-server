package com.example.service.auth.config;

import com.example.service.auth.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * For configuring the end users recognized by this Authorization Server
 */
@Order(-20)
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private static final String LOGIN = "/login";

  private final AuthUserDetailsService authUserDetailsService;

  private final AuthenticationConfiguration authenticationConfiguration;

  private AuthenticationManager authenticationManager;

  @Autowired
  public WebSecurityConfig(final AuthenticationConfiguration authenticationConfiguration, final AuthUserDetailsService authUserDetailsService) {
    this.authenticationConfiguration = authenticationConfiguration;
    this.authUserDetailsService = authUserDetailsService;
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth
        .parentAuthenticationManager(authenticationManager)
        .userDetailsService(authUserDetailsService)
        .passwordEncoder(new BCryptPasswordEncoder());
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    // @formatter:off
    http
        .requestMatchers().antMatchers(LOGIN, "/oauth/authorize", "/oauth/confirm_access")
        .and()
        .logout().permitAll()
        .and()
        .authorizeRequests().anyRequest().authenticated()
        .and()
        .formLogin().loginPage(LOGIN).permitAll();
    // @formatter:on
  }

}