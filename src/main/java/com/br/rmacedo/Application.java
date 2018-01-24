package com.br.rmacedo;

import com.br.rmacedo.service.SecurityServiceImpl;
import com.br.rmacedo.service.interfaces.SecurityService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class Application {

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();

	}

	@Bean
	public SecurityService securityService() {
		return new SecurityServiceImpl();

	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
