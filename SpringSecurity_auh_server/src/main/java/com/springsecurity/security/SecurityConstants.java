package com.springsecurity.security;

//import com.springsecurity.entities.AppRole;

public class SecurityConstants {

	public static final String DEFAULTROLERO = "READONLY";
	public static final String ROLEADMIN = "ADMIN";
	public static final String ROLEOPS = "OPS";
	public static final String ROLEMON = "MON";
	public static final String ROLEUSER = "USER";

	
	public static final String SECRET = "mysecret";
	public static final long EXPIRATION_TIME = 10*24*3600*1000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";
}
