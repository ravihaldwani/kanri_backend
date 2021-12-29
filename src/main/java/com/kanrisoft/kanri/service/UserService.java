package com.kanrisoft.kanri.service;

import java.util.List;

import com.kanrisoft.kanri.domain.User;

public interface UserService {
	public User save(User user);

	public List<User> findAll();

	public User findOne(String username);
}
