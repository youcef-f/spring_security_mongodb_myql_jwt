package com.springsecurity.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springsecurity.entities.AppUser;

public interface IAppUserRepository extends JpaRepository<AppUser, Long> {

	// cette methode n'existe pas defaut dans jparepository
	public AppUser findByUsername(String username);

}
