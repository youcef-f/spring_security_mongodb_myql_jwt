package com.springsecurity;

import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.springsecurity.dao.IAppRoleRepository;
import com.springsecurity.dao.IAppUserRepository;
import com.springsecurity.dao.ITaskRepository;
import com.springsecurity.entities.AppRole;
import com.springsecurity.entities.AppUser;
import com.springsecurity.entities.Task;
import com.springsecurity.security.SecurityConstants;
import com.springsecurity.service.IAccountService;

@SpringBootApplication
public class SpringSecurityApplication implements CommandLineRunner {

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	private IAppRoleRepository appRoleRepository;

	@Autowired
	private IAppUserRepository appUserRepository;

	@Autowired
	private ITaskRepository taskRepository;

	@Autowired
	private IAccountService accountService;

	
	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		AppRole roleadmin = accountService.saveRole(new AppRole(null, SecurityConstants.ROLEADMIN));
		AppRole roleops = accountService.saveRole(new AppRole(null, SecurityConstants.ROLEOPS));
		AppRole rolemon = accountService.saveRole(new AppRole(null, SecurityConstants.ROLEMON));
		AppRole roleread = accountService.saveRole(new AppRole(null, SecurityConstants.DEFAULTROLERO));
		AppRole roleuser = accountService.saveRole(new AppRole(null, SecurityConstants.ROLEUSER));


		
		accountService.saveUser(new AppUser(null, "admin", "1234", null));
		accountService.saveUser(new AppUser(null, "userops", "1234", null));
		accountService.saveUser(new AppUser(null, "useremon", "1234", null));
		accountService.saveUser(new AppUser(null, "user", "1234", null));
		
		accountService.addRoleToUser("admin", roleadmin.getRoleName());
		accountService.addRoleToUser("admin", roleops.getRoleName());
		accountService.addRoleToUser("admin", rolemon.getRoleName());
		accountService.addRoleToUser("userops", roleops.getRoleName());
		accountService.addRoleToUser("useremon", rolemon.getRoleName());
		accountService.addRoleToUser("user", roleuser.getRoleName());

		
		// Creer quelque tache au demarrage de l'application
		Stream.of("tache1", "tache2", "tache3").forEach(t -> {
			taskRepository.save(new Task(null, t));
		});

		// findAll() return List<Task>
		taskRepository.findAll().forEach(t -> {
			System.out.println("task  id: " + t.getId() + " taskname: " + t.getTaskName());
		});

		// findAll() return List<Task>
		appRoleRepository.findAll().forEach(r -> {
			System.out.println("user id: " + r.getId() + " role: " + r.getRoleName());
		});

		// findAll() return List<Task>
		appUserRepository.findAll().forEach(u -> {
			System.out.println(
					"role id: " + u.getId() + " username : " + u.getUsername() + " password: 1234 " + u.getPassword());
		});
	}

}
