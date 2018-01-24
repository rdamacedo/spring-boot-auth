package com.br.rmacedo.service;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.exception.UserExistsException;
import com.br.rmacedo.repository.ApplicationUserRepository;
import com.br.rmacedo.service.interfaces.SecurityService;
import com.br.rmacedo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.NoPermissionException;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private ApplicationUserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	SecurityService securityService;

	@Override
	public ApplicationUser save(ApplicationUser user) throws UserExistsException, NoPermissionException {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		if (userRepository.findByUsername(user.getUsername()) != null) {
			throw new UserExistsException();
		}
		user.setToken(securityService.generateToken(user.getUsername()));

		return userRepository.save(user);
	}

	@Override
	public ApplicationUser findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public ApplicationUser getOne(Long id) throws NoPermissionException {
		String userEmail = getAuthenticatedUser();
		ApplicationUser applicationUser = userRepository.findByUsername(userEmail);

		if (applicationUser.getId() == id) {
			return userRepository.getOne(id);
		} else {
			throw new NoPermissionException();
		}
	}

	private String getAuthenticatedUser() {
		return securityService.getAuthenticatedUser();
	}


	@Override
	public void delete(Long id) {
		String userEmail = getAuthenticatedUser();
		ApplicationUser applicationUser = userRepository.findByUsername(userEmail);

		if (applicationUser.getId() == id) {
			userRepository.delete(id);
		}
	}

	@Override
	public ApplicationUser update(Long id, ApplicationUser user) throws NoPermissionException {
		String userEmail = getAuthenticatedUser();
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

	@Override
	public void updateLastLogin(String username) {
		ApplicationUser applicationUser = userRepository.findByUsername(username);
		applicationUser.setLastLogin(new Date());
		userRepository.save(applicationUser);
	}

	@Override
	public List<ApplicationUser> listAll() {
		return userRepository.findAll();
	}
}
