package com.columns.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.columns.models.User;
import com.columns.payload.request.LoginRequest;
import com.columns.payload.request.SignupRequest;
import com.columns.payload.response.JwtResponse;
import com.columns.repository.UserRepository;
import com.columns.security.services.UserDetailsImpl;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepository userRepository;
	
	@PostMapping("/get")
	public ResponseEntity<Optional<User>> getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Optional<User> user = userRepository.findByUsername(currentPrincipalName);
		return ResponseEntity.ok(user);
	}
	
	@PostMapping("/save")
	public  ResponseEntity<User> saveUser(@Valid @RequestBody User user) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Optional<User> currentUser = 
				userRepository.findByUsername(currentPrincipalName);
		if(user.getPassword() == null || user.getPassword().isEmpty()) {
			String currentPassword = currentUser.get().getPassword();
			user.setPassword(currentPassword);
		}
		if(user.getUsername().equals(currentUser.get().getUsername())) {
			user = userRepository.save(user);
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.ok(null);
		}	
	}

}