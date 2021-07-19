package com.market.skin.security.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.market.skin.service.UsersService;

@Component
public class AuthTokenFilter extends OncePerRequestFilter{
	private final JwtUtils jwtUtils;
	private final UsersService usersService;
	private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

	public AuthTokenFilter(JwtUtils jwtUtils, UsersService service){
		this.jwtUtils = jwtUtils;
		this.usersService = service;
	}

	private String parseJwt(HttpServletRequest request){
		String headerAuth = request.getHeader("Authorization");
		if(StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer")){
			return headerAuth.substring(7, headerAuth.length());
		}
		return null;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		try{
			String jwt = this.parseJwt(request);
			if(jwt != null && this.jwtUtils.validateJwtToken(jwt)){
				String username = this.jwtUtils.getUserNameFromJwtToken(jwt);
				UserDetails userDetails = this.usersService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch(Exception err){
			logger.error("Cant set user authentication: {}", err);
		}
		filterChain.doFilter(request, response);
	}
	
}