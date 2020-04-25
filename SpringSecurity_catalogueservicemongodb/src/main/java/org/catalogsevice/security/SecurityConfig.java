package org.catalogsevice.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/*
	 * @Override protected void configure(AuthenticationManagerBuilder auth) throws
	 * Exception {
	 * 
	 * 
	 * 
	 * BCryptPasswordEncoder bCryptPasswordEncoder = getBCryptPasswordEncoder();
	 * 
	 * auth.inMemoryAuthentication().withUser("admin").password(
	 * bCryptPasswordEncoder.encode("1234")).roles("ADMIN", "USER");
	 * auth.inMemoryAuthentication().withUser("user1").password(
	 * bCryptPasswordEncoder.encode("1234")).roles("USER");
	 * 
	 * }
	 * 
	 * 
	 * @Bean public BCryptPasswordEncoder getBCryptPasswordEncoder() { return new
	 * BCryptPasswordEncoder(); }
	 * 
	 */

	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub

		http.csrf().disable(); // .authorizeRequests().anyRequest().permitAll();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/categories/**").permitAll();	
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/products/**").permitAll();
		http.authorizeRequests().antMatchers("/categories/**").hasAuthority("ADMIN"); // POST;DETELE;PUT ..
		http.authorizeRequests().antMatchers("/products/**").hasAuthority("USER"); // POST;DETELE;PUT ..
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

	}
}
