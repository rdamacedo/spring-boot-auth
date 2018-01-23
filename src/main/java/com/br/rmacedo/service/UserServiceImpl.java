package com.br.rmacedo.service;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.exception.UserExistsException;
import com.br.rmacedo.repository.ApplicationUserRepository;
import com.br.rmacedo.service.interfaces.SecurityService;
import com.br.rmacedo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Date;

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

	@Override
	public ApplicationUser update(Long id, ApplicationUser user) throws NoPermissionException {
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

		String userEmail = (String) usernamePasswordAuthenticationToken.getPrincipal();
		ApplicationUser applicationUser = userRepository.findByUsername(userEmail);

		if (applicationUser.getId() == id) {
			applicationUser.setPhones(user.getPhones());
			//fixme: this should not be here, should be in db
			applicationUser.setModified(new Date());
			return userRepository.save(applicationUser);
		} else {
			throw new NoPermissionException();
		}
	}
}
