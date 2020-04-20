package com.protech.matricula.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.protech.matricula.service.impl.CustomUserDetailsService;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Autowired
	public void configurerGlobal(AuthenticationManagerBuilder builder) throws Exception{
		builder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/js/**","/css/**","/vendor/**").permitAll()
		.anyRequest().authenticated()
		.and()
		.formLogin().loginPage("/login").defaultSuccessUrl("/").permitAll().failureUrl("/login-error")
		.and()
		.logout().permitAll();
	}

}
