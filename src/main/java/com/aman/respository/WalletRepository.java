package com.aman.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aman.entity.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long>{
	
	Optional<Wallet> findByUserName(String userName);

}
