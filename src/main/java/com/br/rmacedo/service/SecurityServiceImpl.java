package com.br.rmacedo.service;

import com.br.rmacedo.service.interfaces.SecurityService;
import com.br.rmacedo.security.SecurityConstants;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Date;

@Service
public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

	private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);

	@Override
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if (userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}

		return null;
	}


	@Override
	public String getAuthenticatedUser() {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
		return (String) usernamePasswordAuthenticationToken.getPrincipal();
	}

	@Override
	public UsernamePasswordAuthenticationToken autologin(String username, String password) throws NoPermissionException {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

		authenticationManager.authenticate(usernamePasswordAuthenticationToken);

		if (usernamePasswordAuthenticationToken.isAuthenticated()) {
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			logger.debug(String.format("Auto login %s successfully!", username));
			return usernamePasswordAuthenticationToken;
		} else {
			throw new NoPermissionException();
		}
	}


	@Override
	public String generateToken(String username){
		String token = Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)
				.compact();

		return SecurityConstants.TOKEN_PREFIX + token;
	}
}
