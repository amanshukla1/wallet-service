package com.aman.service;

import com.aman.entity.User;

public interface WalletService {

	void credit(String userName, double amount);
	void debit(String userName, double amount);
	Double getBalance(String userName);
	void createUser(User user);

}
