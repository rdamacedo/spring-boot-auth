package com.br.rmacedo.controller;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.repository.ApplicationUserRepository;
import com.br.rmacedo.security.JWTAuthenticationFilter;
import com.br.rmacedo.service.SecurityServiceImpl;
import com.br.rmacedo.service.UserDetailsServiceImpl;
import com.br.rmacedo.service.UserServiceImpl;
import com.br.rmacedo.service.interfaces.SecurityService;
import com.br.rmacedo.service.interfaces.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class UserControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	UserService userService;

	@MockBean
	AuthenticationManager authenticationManager;

	@MockBean
	ApplicationUserRepository applicationUserRepository;

	@MockBean
	UserService userRepository;

	private JacksonTester<ApplicationUser> jsonApplicationUser;

//	@Autowired
//	private WebApplicationContext context;
//
//	@Autowired
//	private Filter springSecurityFilterChain;


	ApplicationUser user = new ApplicationUser();
	String userName = "admin";
	Long id = 1L;

	@Before
	public void setUp() {
		user.setUsername(userName);
		JacksonTester.initFields(this, new ObjectMapper());

		Mockito.when(applicationUserRepository.findByUsername(userName)).thenReturn(user);
		Mockito.when(userRepository.findByUsername(userName)).thenReturn(user);

//		mvc = MockMvcBuilders.webAppContextSetup(context)
//				.addFilters(springSecurityFilterChain).build();

	}

//	@Test
//	public void signUp_withoutAuthentication() throws Exception {
//		Authentication authentication = Mockito.mock(Authentication.class);
//		Mockito.when(authentication.getName()).thenReturn(userName);
//		Mockito.when(
//				authenticationManager.authenticate(Mockito
//						.any(UsernamePasswordAuthenticationToken.class)))
//				.thenReturn(authentication);
//
//		MockHttpServletResponse response = this.mvc.perform(post("/users")
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(jsonApplicationUser.write(new ApplicationUser(userName)).getJson()))
//				.andReturn().getResponse();
//
//
//		assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//	}
//
	@Test
	public void showUserById_NotAuthorized() throws Exception {
//		given(userService.getOne(1L)).willReturn(user);
//
//		this.mvc.perform(get("/users/1")
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(status().isUnauthorized());
	}


}
