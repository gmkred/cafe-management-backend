package com.gmkr.cafe.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.gmkr.cafe.jwt.CustomerUserDetailsService;
import com.gmkr.cafe.jwt.JwtFilter;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableWebSecurity
@Slf4j
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomerUserDetailsService cuds;
	@Autowired
	private JwtFilter filter;

	@Bean
	public PasswordEncoder passwordEncoder() {
		log.info("triggered passwordEncoder { } : ");
		return NoOpPasswordEncoder.getInstance();
	}

//Configures the authentication manager, which is responsible for validating user credentials.
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(cuds);
	}

//Creates a bean for the AuthenticationManager, which is a central component in Spring Security authentication
	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	public AuthenticationManager authenticationManagerBuilder() throws Exception {
		return super.authenticationManagerBean();
	}

	public void configure(HttpSecurity http) throws Exception {
		http.cors().configurationSource(req -> new CorsConfiguration().applyPermitDefaultValues()).and().csrf()
				.disable().authorizeRequests().antMatchers("/user/login", "/user/signUp", "/user/forgotPassword")
				.permitAll().antMatchers(AUTH_WHITELIST).permitAll().anyRequest().authenticated().and()
				.exceptionHandling().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		Adding the JwtFilter before the UsernamePasswordAuthenticationFilter to handle JWT authentication.
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	}

	private static final String[] AUTH_WHITELIST = { "/swagger-ui/**", "/swagger-ui.html", "/v3/api-docs.yaml",
			"/v3/api-docs/**", "/api/v1/auth/**" };

	
}
