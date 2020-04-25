package com.springsecurity.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springsecurity.dao.IAppRoleRepository;
import com.springsecurity.dao.IAppUserRepository;
import com.springsecurity.entities.AppRole;
import com.springsecurity.entities.AppUser;
import com.springsecurity.service.IAccountService;


@Service
@Transactional
public class AccountServiceImplement implements IAccountService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private IAppUserRepository appUserRepository;

	@Autowired
	private IAppRoleRepository appRoleRepository;

	@Override
	public AppUser saveUser(AppUser user) {
		String passwordBcyrptEncoder = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(passwordBcyrptEncoder);
		return appUserRepository.save(user);
	}

	@Override
	public AppRole saveRole(AppRole role) {
		return appRoleRepository.save(role);
	}

	@Override
	public void addRoleToUser(String username, String roleName) {
		AppUser user = appUserRepository.findByUsername(username);
		AppRole role = appRoleRepository.findByRoleName(roleName);
		// recupere tous les role et ajoute un nouveau
		user.getRoles().add(role);
	}

	@Override
	public AppUser findUserByUsername(String username) {
		return appUserRepository.findByUsername(username);
	}

}
