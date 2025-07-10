package com.aman.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aman.entity.User;
import com.aman.entity.Wallet;
import com.aman.respository.WalletRepository;
import com.aman.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/wallet/admin")
@Slf4j
public class AdminController {
	
	private UserService userService;
	
	public AdminController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/all-users")
	public ResponseEntity<List<User>> fetchAllUsers() {
		log.info("inside fetchAllUSers!!!");
		List<User> userList = userService.fetchAllUsers();
		return new ResponseEntity<>(userList,HttpStatus.OK);
	}
	
	@GetMapping("/user")
	public ResponseEntity<User> getUserDetail(@RequestParam("userName") String userName) {
		User user = userService.fetchUser(userName);
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

}
