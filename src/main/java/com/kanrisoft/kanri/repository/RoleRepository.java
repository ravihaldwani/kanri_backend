package com.kanrisoft.kanri.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kanrisoft.kanri.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	public Role findRoleByName(String name);
}
