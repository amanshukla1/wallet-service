package com.aman.service.impl;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.aman.entity.User;
import com.aman.entity.Wallet;
import com.aman.exception.DuplicateUserException;
import com.aman.exception.UserNotFoundException;
import com.aman.respository.UserRepository;
import com.aman.service.UserService;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public List<User> fetchAllUsers() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@Override
	@Transactional
	public void createUser(User user) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
	}

	@Override
	public User fetchUser(String userName) {
		User user = userRepository.findByUserName(userName)
				.orElseThrow(() -> new UserNotFoundException("User not found!!"));
		return user;
	}
	
}
