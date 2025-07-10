package com.aman.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.aman.entity.User;
import com.aman.respository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	public UserDetailsServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Inside loadUserByUsername username - {}", username);

		User user = userRepository.findByUserName(username)
			.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		log.info("Username: {}, Password: {}, Role: {}", user.getUserName(), user.getPassword(), user.getRole());

		UserDetails userDetail = org.springframework.security.core.userdetails.User.builder()
			.username(user.getUserName())
			.password(user.getPassword())
			.roles(user.getRole())
			.build();

		log.info("Built UserDetails: {}", userDetail);
		return userDetail;
	}
}
