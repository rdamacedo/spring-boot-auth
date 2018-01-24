package com.br.rmacedo.service.interfaces;


import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.naming.NoPermissionException;

public interface SecurityService {
	String findLoggedInUsername();

	UsernamePasswordAuthenticationToken getAuthenticatedUser();

	UsernamePasswordAuthenticationToken autologin(String username, String password) throws NoPermissionException;

	String generateToken(String username);
}
