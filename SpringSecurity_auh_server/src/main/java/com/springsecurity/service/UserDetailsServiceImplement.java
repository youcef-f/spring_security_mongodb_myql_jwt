package com.springsecurity.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.springsecurity.entities.AppUser;

@Service
public class UserDetailsServiceImplement implements UserDetailsService {

	@Autowired
	private IAccountService accountService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		AppUser user = accountService.findUserByUsername(username);

		if (user == null) {
			throw new UsernameNotFoundException(username);
		}

		System.out.println("----------------------------------------------------------------");
		System.out.println("user: " + user.getUsername() + " password: " + user.getPassword());

		Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		// EAGER permet d'aller recuperer les role d'un user
		user.getRoles().forEach(role -> {

			System.out.println("role: " + role.getRoleName());
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
		});

		System.out.println("------------------------------------------------------------");

		/*
		 * UserBuilder userBuild =
		 * org.springframework.security.core.userdetails.User.withUsername(user.
		 * getUsername());
		 * 
		 * // userBuild.username(user.getUsername());
		 * userBuild.password(user.getPassword()); userBuild.authorities(authorities);
		 * return userBuild.build();
		 */

		return new User(user.getUsername(), user.getPassword(), authorities);

	}

	

}
