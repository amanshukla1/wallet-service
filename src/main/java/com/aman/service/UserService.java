package com.aman.service;

import java.util.List;

import com.aman.entity.User;

public interface UserService {
	
	public List<User> fetchAllUsers();
	public void createUser(User user);
	public User fetchUser(String userName);

}
