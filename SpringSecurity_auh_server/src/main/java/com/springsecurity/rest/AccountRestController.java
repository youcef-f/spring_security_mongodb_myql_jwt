package com.springsecurity.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.entities.AppUser;
import com.springsecurity.service.IAccountService;
import  com.springsecurity.security.*;

//@CrossOrigin("*")
@RestController
public class AccountRestController {

	
	@Autowired
	private IAccountService accountService;

	@GetMapping("/healthcheck")
	public String healthcheck() {
		System.out.println("hello world");
		return "hello world";
	}

	@PostMapping("/register")
	public AppUser registerUser(@RequestBody RegisterUserFormValidation registerUserFormValidation) {

		//String defaultRole = "READ";

		String userRegister = registerUserFormValidation.getUsername();

		AppUser appUserInBase =   accountService.findUserByUsername(userRegister) ;
		
		
		// user aleardy exists
		if (appUserInBase!=null)
			throw new RuntimeException("User already exists");
			
		// password mismatch userRegister!=null |
		// 
		if (registerUserFormValidation.getPassword().isEmpty() | registerUserFormValidation.getUsername().isEmpty() )
			throw new RuntimeException("Registration error: User or password cannot empty");

		// password mismatch userRegister!=null |
		if (!registerUserFormValidation.getPassword().equals(registerUserFormValidation.getRepassword()))
			throw new RuntimeException("Registration error: Confirm you passwod");



		// user connot be null

		AppUser appUser = new AppUser();
		appUser.setUsername(registerUserFormValidation.getUsername());
		appUser.setPassword(registerUserFormValidation.getPassword());
		accountService.saveUser(appUser); // retourne appUser par référence
		
		accountService.addRoleToUser(userRegister, SecurityConstants.ROLEUSER);

		System.out.println("username: " + appUser.getUsername() + " password: " + appUser.getPassword());

		return appUser;

	}
}
