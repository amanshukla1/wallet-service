package com.aman.service;

public interface WalletService {

	void credit(String userName, double amount);
	void debit(String userName, double amount);
	Double getBalance(String userName);

}
