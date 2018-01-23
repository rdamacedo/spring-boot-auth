package com.br.rmacedo.service.interfaces;


import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.exception.UserExistsException;

import javax.naming.NoPermissionException;

public interface UserService {
	ApplicationUser save(ApplicationUser user) throws UserExistsException;

	ApplicationUser findByUsername(String username);

	Object getOne(Long id) throws NoPermissionException;

	void delete(Long id);

	ApplicationUser update(Long id, ApplicationUser user);
}
