package com.spring.microservices.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//In memory authentication.
@Configuration
//Using below annotation, Spring will not use its own authentication.
@EnableWebSecurity
public class BasicAuthenticationHandler extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		//Prior to Spring Security 5.0 the default PasswordEncoder was NoOpPasswordEncoder
		//which required plain text passwords. 
		//In Spring Security 5, the default is DelegatingPasswordEncoder, which required Password Storage Format.
		//Using {noop} as NoOpPasswordEncoder.
		//We can use defaultPasswordEncoder
		auth.inMemoryAuthentication()
		.withUser("user").password("{noop}password").roles("USER")
		.and()
		.withUser("admin").password("{noop}password").roles("USER", "ADMIN");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
				http
				.httpBasic().and().authorizeRequests()
				.antMatchers(HttpMethod.GET, "/users").hasRole("USER")
				.antMatchers(HttpMethod.GET, "/users/**").hasRole("ADMIN")
				.and()
				.csrf()
				.disable()
				.formLogin()
				.disable();

	}
}