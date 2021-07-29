package com.market.skin;

import com.market.skin.security.jwt.AuthTokenFilter;
import com.market.skin.security.jwt.JwtUtils;
import com.market.skin.service.UsersService;
import com.market.skin.controller.UsersController;
import com.market.skin.repository.UsersRepository;
import com.market.skin.security.jwt.AuthEntryPointJwt;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration 
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    // securedEnabled = true,
    // jsr250Enabled = true,
    prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    private final UsersService service;
	private final AuthEntryPointJwt unauthorizedHandler;
	private final JwtUtils jwtUtils;

	public WebSecurityConfig (UsersService usersService, AuthEntryPointJwt unauthorizedHandler, JwtUtils jwtUtils){
		this.service = usersService;
		this.unauthorizedHandler = unauthorizedHandler;
		this.jwtUtils = jwtUtils;
	}

	@Bean
	UsersController usersController(UserDetailsService userDetailsService){
		return new UsersController(userDetailsService);
	}
	@Bean
	UsersService usersService(UsersRepository usersRepository){
		return new UsersService(usersRepository);
	}

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter(){
		return new AuthTokenFilter(this.jwtUtils, this.service);
	}

	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(this.service).passwordEncoder(passwordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and().authorizeRequests().antMatchers("/api/auth/**", "/**" ).permitAll()
			.anyRequest().authenticated();

		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
}