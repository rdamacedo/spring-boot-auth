package com.br.rmacedo.service;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.domain.UserPhone;
import com.br.rmacedo.repository.ApplicationUserRepository;
import com.br.rmacedo.service.interfaces.SecurityService;
import com.br.rmacedo.service.interfaces.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hsqldb.lib.tar.TarHeaderField.name;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	UserDetailsService userDetailsService;

	@MockBean
	SecurityService securityService;

	@TestConfiguration
	static class UserServiceImplTestContextConfiguration {

		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}

		@Bean
		public BCryptPasswordEncoder bCryptPasswordEncoder() {
			return new BCryptPasswordEncoder();

		}

	}

	@Autowired
	private UserService userService;

	@MockBean
	private ApplicationUserRepository userRepository;

	ApplicationUser user = new ApplicationUser();
	String name = "admin";
	Long id = 1L;

	@Before
	public void setUp() {
		user.setUsername("admin");
		user.setId(id);

		Mockito.when(securityService.getAuthenticatedUser())
				.thenReturn("admin");

		Mockito.when(userRepository.findByUsername("admin")).thenReturn(user);

		Mockito.when(userRepository.getOne(id)).thenReturn(user);
	}

	@Test
	public void findByUsername() throws Exception {
		ApplicationUser found = userService.findByUsername(name);

		assertTrue(found.getUsername().equals(name));
	}

	@Test
	public void getOne() throws Exception {

		ApplicationUser found = userService.getOne(id);

		assertNotNull(found);
		assertTrue(found.getId() == id);
	}

	@Test
	public void save() throws Exception {
		ApplicationUser updatedUser =  user;
		List<UserPhone> phoneList = new ArrayList<>();
		phoneList.add(new UserPhone("999999999", "21"));
		updatedUser.setPhones(phoneList);

		Mockito.when(userRepository.save(updatedUser)).thenReturn(updatedUser);

		ApplicationUser found  = userService.update(updatedUser.getId(),updatedUser);

		assertNotNull(found.getPhones());
	}

}
