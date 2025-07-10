package com.aman.service.impl;

import org.springframework.stereotype.Service;

import com.aman.entity.Wallet;
import com.aman.exception.InsufficientBalanceException;
import com.aman.exception.WalletNotFoundException;
import com.aman.respository.WalletRepository;
import com.aman.service.WalletService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {
	
	private WalletRepository walletRepository;
	
	public WalletServiceImpl(WalletRepository walletRepository) {
		this.walletRepository = walletRepository;
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
		Wallet wallet = walletRepository.findByUserName(userName).orElseThrow(() -> new WalletNotFoundException("Wallet not found!!"));
		if (wallet.getBalance() < amount) {
            throw new InsufficientBalanceException("Not enough balance!!");
        }
		if(wallet.getBalance() >= amount) {
			wallet.setBalance(wallet.getBalance() - amount);
		}
		walletRepository.save(wallet);
		log.info("Debited {} from user {}", amount, userName);	
	}

	@Override
	public Double getBalance(String userName) {
		Wallet wallet = walletRepository.findByUserName(userName).orElseThrow(() -> new WalletNotFoundException("Wallet not found!!"));
        log.info("Checked balance for user {}", userName);
        return wallet.getBalance();
	}

}
