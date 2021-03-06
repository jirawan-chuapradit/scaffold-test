package com.tvisarut.scaffold.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.tvisarut.scaffold.filter.JWTRequestFilter;
import com.tvisarut.scaffold.service.AuthService;

@Configuration
@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthService authUser;

	@Autowired
	private JWTRequestFilter accessTokenRequestFilter;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(authUser);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// detach the login URL from authentication middleware
		http.csrf().disable().authorizeRequests()
				.antMatchers("/login", "/v2/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**","/swagger.json")
				.permitAll().anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.addFilterBefore(accessTokenRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {    
	    web.ignoring().antMatchers("/v2/api-docs/**");
	    web.ignoring().antMatchers("/swagger.json");
	    web.ignoring().antMatchers("/swagger-ui.html");
	    web.ignoring().antMatchers("/swagger-resources/**");
	    web.ignoring().antMatchers("/webjars/**");
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
//		return NoOpPasswordEncoder.getInstance();
	}
}
