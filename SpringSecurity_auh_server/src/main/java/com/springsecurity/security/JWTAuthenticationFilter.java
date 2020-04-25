package com.springsecurity.security;

import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecurity.entities.AppUser;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		
		
		AppUser user = null;
		try {
			// deseralise un object de type Json vers un objet AppUser
			user = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		System.out.println("------------------- methode  attemptAuthentication :  donner envoyer par user ---------");
		System.out.println("uesr: " + user.getUsername() + " password: " + user.getPassword());
		return authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {

		System.out
				.println("------------------- methode  successfulAuthentication :  donner envoyer par user ---------");
		// System.out.println("principe: " + authResult.getPrincipal()+ " Credential: "
		// + authResult.getCredentials().toString());

		
		User springUser = (User) authResult.getPrincipal();
		
		// creer un jwt avec les claims
		String jwtToken = Jwts.builder()
				.setSubject(springUser.getUsername())  // claim sub
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME)) // claim exp
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.SECRET)  
				.claim("roles", springUser.getAuthorities()) // costums claims ( private claims )
				.compact();
		
		// Ajouter entete
		response.addHeader(
				SecurityConstants.HEADER_STRING,  // Authorization
				SecurityConstants.TOKEN_PREFIX + jwtToken  // Bearer xxxxxxxx
				);

		// super.successfulAuthentication(request, response, chain, authResult);

	}
	
	
	
}