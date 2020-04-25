package com.springsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.entities.AppRole;

public interface IAppRoleRepository extends JpaRepository<AppRole,Long>{
	
	public AppRole findByRoleName (String roleName);

}
