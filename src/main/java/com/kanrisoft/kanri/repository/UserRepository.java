package com.kanrisoft.kanri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanrisoft.kanri.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByUsername(String username);
}
