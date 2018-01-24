package com.br.rmacedo.service;

import com.br.rmacedo.domain.ApplicationUser;
import com.br.rmacedo.repository.ApplicationUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
    private ApplicationUserRepository applicationUserRepository;

	public UserDetailsServiceImpl() {

	}

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(email);

        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(applicationUser.getUsername(), applicationUser.getPassword(), emptyList());
    }



}
