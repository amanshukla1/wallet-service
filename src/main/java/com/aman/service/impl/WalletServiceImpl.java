package com.aman.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.aman.entity.User;
import com.aman.entity.Wallet;
import com.aman.exception.InsufficientBalanceException;
import com.aman.exception.WalletNotFoundException;
import com.aman.respository.WalletRepository;
import com.aman.service.UserService;
import com.aman.service.WalletService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {
	
	private WalletRepository walletRepository;
	private UserService userService;
	private WalletLockManager lockManager;
	
	public WalletServiceImpl(WalletRepository walletRepository, UserService userService, WalletLockManager lockManager) {
		this.walletRepository = walletRepository;
		this.userService = userService;
		this.lockManager = lockManager;
	}

	@Override
	public void credit(String userName, double amount) {
		Wallet wallet = walletRepository.findByUserName(userName).orElseThrow(() -> new WalletNotFoundException("Wallet not found!!"));
		wallet.setBalance(wallet.getBalance() + amount);
		walletRepository.save(wallet);
		log.info("Credited {} to user {}", amount, userName);
	}

	@Override
	public void debit(String userName, double amount) {
		Lock lock = lockManager.getLock(userName);
		boolean isLockAcquired = false;

		try {
			isLockAcquired = lock.tryLock(10, TimeUnit.SECONDS);
			if (!isLockAcquired) {
				throw new RuntimeException("Could not acquire lock to perform debit");
			}

			Wallet wallet = walletRepository.findByUserName(userName)
					.orElseThrow(() -> new WalletNotFoundException("Wallet not found!!"));

			if (wallet.getBalance() < amount) {
				throw new InsufficientBalanceException("Not enough balance!!");
			}

			wallet.setBalance(wallet.getBalance() - amount);
			walletRepository.save(wallet);
			log.info("Debited {} from user {}", amount, userName);

		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Thread was interrupted while waiting for lock", e);
		} finally {
			if (isLockAcquired) {
				lock.unlock();
			}
		}
	}

	@Override
	public Double getBalance(String userName) {
		Wallet wallet = walletRepository.findByUserName(userName).orElseThrow(() -> new WalletNotFoundException("Wallet not found!!"));
        log.info("Checked balance for user {}", userName);
        return wallet.getBalance();
	}

	@Override
	@Transactional
	public void createUser(User user) {
		try {
			Wallet wallet = new Wallet();
			wallet.setBalance(0.0);
			wallet.setUserName(user.getUserName());
			walletRepository.save(wallet);
			user.setWallet(wallet);
			user.setRole("USER");
			userService.createUser(user);
		} catch (DataIntegrityViolationException e) {
			log.warn("Duplicate user creation attempted for username: {}", user.getUserName());
			throw new IllegalArgumentException("Username already exists");
		}

	}

}
