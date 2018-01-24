package com.br.rmacedo.security;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.service.UserServiceImpl;
import com.br.rmacedo.service.interfaces.SecurityService;
import com.br.rmacedo.service.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	private SecurityService securityService;

	private UserService userService;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, SecurityService securityService, UserService userService) {
		this.authenticationManager = authenticationManager;
		this.securityService = securityService;
		this.userService = userService;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			ApplicationUser creds = new ObjectMapper().readValue(req.getInputStream(), ApplicationUser.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							creds.getUsername(),
							creds.getPassword(),
							new ArrayList<>())
			);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req,
											HttpServletResponse res,
											FilterChain chain,
											Authentication auth) throws IOException, ServletException {
		res.addHeader(SecurityConstants.HEADER_STRING, securityService.generateToken(((User) auth.getPrincipal()).getUsername()));
		userService.updateLastLogin(((User) auth.getPrincipal()).getUsername());
	}

}
