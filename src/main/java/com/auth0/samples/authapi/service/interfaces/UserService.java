package com.auth0.samples.authapi.service.interfaces;


import com.auth0.samples.authapi.domain.ApplicationUser;
import com.auth0.samples.authapi.exception.UserExistsException;

import javax.naming.NoPermissionException;

public interface UserService {
	ApplicationUser save(ApplicationUser user) throws UserExistsException;

	ApplicationUser findByUsername(String username);

	Object getOne(Long id) throws NoPermissionException;

	void delete(Long id);

	ApplicationUser update(Long id, ApplicationUser user);
}
