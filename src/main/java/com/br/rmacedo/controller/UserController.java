package com.br.rmacedo.controller;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.exception.UserExistsException;
import com.br.rmacedo.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	UserService userService;

	public UserController() {
	}

	@PostMapping("/sign-up")
	public ApplicationUser signUp(@RequestBody ApplicationUser user) throws UserExistsException {
		return userService.save(user);
	}


	@GetMapping("/{id}")
	public Object get(@PathVariable Long id) throws Exception {
		return userService.getOne(id);
	}


	@PutMapping("/{id}")
	public ApplicationUser put(@PathVariable Long id, @RequestBody ApplicationUser user) {
		return userService.update(id, user);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		userService.delete(id);
	}
}
