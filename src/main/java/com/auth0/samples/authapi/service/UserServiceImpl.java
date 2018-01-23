package com.auth0.samples.authapi.service;

import com.auth0.samples.authapi.domain.ApplicationUser;
import com.auth0.samples.authapi.exception.UserExistsException;
import com.auth0.samples.authapi.repository.ApplicationUserRepository;
import com.auth0.samples.authapi.service.interfaces.SecurityService;
import com.auth0.samples.authapi.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private ApplicationUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	SecurityService securityService;

	@Override
	public ApplicationUser save(ApplicationUser user) throws UserExistsException {

		String password = user.getPassword();
		if (userRepository.findByUsername(user.getUsername()) != null) {
			user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			throw new UserExistsException();
		}

		return userRepository.save(user);
	}

	@Override
	public ApplicationUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public Object getOne(Long id) throws NoPermissionException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		String userEmail = (String) usernamePasswordAuthenticationToken.getPrincipal();
		ApplicationUser applicationUser = userRepository.findByUsername(userEmail);

		if (applicationUser.getId() == id) {
			return userRepository.getOne(id);
		} else {
			throw new NoPermissionException();
		}
	}

	@Override
	public void delete(Long id) {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		String userEmail = (String) usernamePasswordAuthenticationToken.getPrincipal();
		ApplicationUser applicationUser = userRepository.findByUsername(userEmail);

		if (applicationUser.getId() == id) {
			userRepository.delete(id);
		}
	}
}
