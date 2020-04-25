package com.springsecurity.service;

import com.springsecurity.entities.AppRole;
import com.springsecurity.entities.AppUser;

public interface IAccountService {

	public AppUser saveUser(AppUser user);
	public AppRole saveRole(AppRole role);
	public void addRoleToUser(String username, String roleName) ;
	public AppUser findUserByUsername(String username);
	
}
