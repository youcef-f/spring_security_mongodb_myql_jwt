package com.springsecurity.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		

		response.addHeader("Access-Control-Allow-Origin", "*");

		response.addHeader("Access-Control-Allow-Headers",
				"Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-RequestHeaders,Authorization");

		response.addHeader("Access-Control-Expose-Headers",
				"Access-Control-Allow-Origin, Access-Control-Allow-Credentials,Authorization");

		response.addHeader("Access-Control-Allow-Methods", "GET,POST,PUT,UPDATE,PATCH");
		// Si la requete HTTP est de type "OPTION" revoir SC_OK ( StatusCode OK 200 )
		if (request.getMethod().equals("OPTIONS")) {
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}

		
		// read header "Authorization" to extract jwt from request
		String jwtToken = request.getHeader(SecurityConstants.HEADER_STRING); // "Authorization: Bear xxxx.yyyy.zzz"

		System.out.println("----------JWTAuthorizationFilter: ----------");
		System.out.println("jwtToke:n " + jwtToken);

		// if jwt is null or not contains "Bearer"
		if (jwtToken == null || !jwtToken.startsWith(SecurityConstants.TOKEN_PREFIX)) {

			filterChain.doFilter(request, response);

			// exit for next filter
			return;
		}

		/*
		 *
		 *
		 * HEADER { "alg": "HS512" } PAYLOAD { "sub": "admin", //getSubject(); "exp":
		 * 1588086688, "roles": [ { "authority": "ADMIN" }, { "authority": "MON" }, {
		 * "authority": "OPS" } ] }
		 */
		Claims claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET) // defini le scret qui à signé le jwt
				.parseClaimsJws(jwtToken.replace(SecurityConstants.TOKEN_PREFIX, "")) // delete prefix "Bearer"
				.getBody();

		// recupere le subject "nom user"
		String username = claims.getSubject();

		// Recupere le claim "roles" dans le JWT { "authority": "ADMIN" }, {
		// "authority": "MON" }, {"authority": "OPS"}
		ArrayList<Map<String, String>> roles = (ArrayList<Map<String, String>>) claims.get("roles");

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		roles.forEach(role -> {
			authorities.add(new SimpleGrantedAuthority(role.get("authority")));
		});

		// null = password. il n'est pas necessaire car nous somme JWT
		UsernamePasswordAuthenticationToken authenticationUserToken = new UsernamePasswordAuthenticationToken(username,
				null, authorities);

		// charger le context de spring security de l'utilisateur connecter
		SecurityContextHolder.getContext().setAuthentication(authenticationUserToken);

		filterChain.doFilter(request, response);
		
		
		
	}

}
