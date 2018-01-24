package com.br.rmacedo.repository;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.service.interfaces.SecurityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationUserRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;


	@Autowired
	private ApplicationUserRepository applicationUserRepository;


	@MockBean
	SecurityService securityService;

	@Test
	public void findByUsername() throws Exception {

		ApplicationUser user =  new ApplicationUser();
		user.setUsername("admin");

		entityManager.persist(user);
		entityManager.flush();

		// when
		ApplicationUser found = applicationUserRepository.findByUsername(user.getUsername());

		// then
		assertTrue(found.getUsername().equals(user.getUsername()));
	}

}
