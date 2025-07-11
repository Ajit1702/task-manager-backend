package com.springbootkafka.task_manager.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springbootkafka.task_manager.model.User;
import com.springbootkafka.task_manager.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(CustomUserDetailsService.class);

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {

		log.info("Attempting to load user by username: {}", username);
		User user = userRepository.findByUsername(username).orElseThrow(() -> {
			log.warn("User not found for username: {}", username);
			return new UsernameNotFoundException("User not found");
		});

		log.info("User found: {}", user.getUsername());
		log.debug("Assigning ROLE_USER to: {}", user.getUsername());

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole())));
	}

}
