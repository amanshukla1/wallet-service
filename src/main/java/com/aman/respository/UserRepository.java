package com.aman.respository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aman.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String userName);

}
