package com.br.rmacedo.service.interfaces;


import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.exception.UserExistsException;

import javax.naming.NoPermissionException;
import java.util.List;

public interface UserService {
	ApplicationUser save(ApplicationUser user) throws UserExistsException;

	ApplicationUser findByUsername(String username);

	ApplicationUser getOne(Long id) throws NoPermissionException;

	void delete(Long id);

	ApplicationUser update(Long id, ApplicationUser user) throws NoPermissionException;

	void updateLastLogin(String username);

	List<ApplicationUser> listAll();
}
