package com.springbootkafka.task_manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootkafka.task_manager.dto.UserDto;
import com.springbootkafka.task_manager.exception.UserAlreadyExistsException;
import com.springbootkafka.task_manager.model.User;
import com.springbootkafka.task_manager.repository.UserRepository;
import com.springbootkafka.task_manager.security.JwtUtil;
import com.springbootkafka.task_manager.service.CustomUserDetailsService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private UserRepository repository;
	@Autowired
	private CustomUserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody User user) {
		if(repository.findByUsername(user.getUsername()).isPresent()) {	
			throw new UserAlreadyExistsException("Username already exists. Please choose a different one.");
		}	
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		repository.save(user);
		return ResponseEntity.ok("User Registered successfully");
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody UserDto request) {
		try {
			 
			authenticationManager.authenticate(
	            new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
	        );
	    } catch (AuthenticationException ex) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
	    }
		final UserDetails user = userDetailsService.loadUserByUsername(request.getUsername());
		return ResponseEntity.ok(jwtUtil.generateToken(user));
	}
	@PutMapping("/update")
	public ResponseEntity<String> updateUser(@RequestBody UserDto dto, Authentication authentication) {
	    String loggedInUsername = authentication.getName();
	    User user = repository.findByUsername(loggedInUsername)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));

	    if (dto.getUsername() != null && !dto.getUsername().isBlank()) {
	        user.setUsername(dto.getUsername());
	    }
	    if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
	        user.setPassword(passwordEncoder.encode(dto.getPassword()));
	    }

	    repository.save(user);
	    return ResponseEntity.ok("User updated successfully");
	}
	
//	@DeleteMapping("/delete")
//	public ResponseEntity<String> deleteUser(Authentication authentication) {
//	    String loggedInUsername = authentication.getName();
//	    User user = repository.findByUsername(loggedInUsername)
//	            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//	    repository.delete(user);
//	    return ResponseEntity.ok("User deleted successfully");
//	}

	

}



