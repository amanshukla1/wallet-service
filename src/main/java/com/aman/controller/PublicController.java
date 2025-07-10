package com.aman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aman.entity.User;
import com.aman.service.WalletService;

@RestController
@RequestMapping("/wallet/public")
public class PublicController {
	
	private WalletService walletService;

	public PublicController(WalletService walletService) {
		this.walletService = walletService;
	}
	
	@GetMapping("/health-check")
	public ResponseEntity<String> healthCheck() {
		return new ResponseEntity<>("Service is up!!", HttpStatus.OK);
	}
	
	@PostMapping("/add-user")
	public ResponseEntity<User> createUser(@RequestBody User user) {
		walletService.createUser(user);
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}

}
