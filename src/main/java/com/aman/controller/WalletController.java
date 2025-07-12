package com.aman.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.aman.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/wallet/user")
@Slf4j
public class WalletController {

	private WalletService walletService;

	public WalletController(WalletService walletService) {
		this.walletService = walletService;
	}

	@GetMapping("/credit")
	public ResponseEntity<String> credit(@RequestParam("amount") double amount) {
		log.info("Credit request - amount {}", amount);

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("userName " + userName);

		walletService.credit(userName, amount);
		return new ResponseEntity<>(amount + " Credited", HttpStatus.OK);
	}

	@GetMapping("/debit")
	public ResponseEntity<String> debit(@RequestParam("amount") double amount) {
		log.info("Debit request - amount {}", amount);

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("userName " + userName);

		walletService.debit(userName, amount);
		return new ResponseEntity<>(amount + " Debited", HttpStatus.OK);
	}

	@GetMapping("/balance")
	public ResponseEntity<String> getBalance() {
		log.info("Balance check request - ");

		String userName = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info("userName " + userName);

		Double balance = walletService.getBalance(userName);
		return new ResponseEntity<>("Balance - " + balance, HttpStatus.OK);
	}

}
