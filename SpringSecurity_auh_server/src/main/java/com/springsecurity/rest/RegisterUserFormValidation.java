package com.springsecurity.rest;

import lombok.Data;

@Data
public class RegisterUserFormValidation {

	private String username;
	private String password;
	private String repassword;
	
}
