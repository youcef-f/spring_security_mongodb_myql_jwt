package com.springsecurity.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {

		/*
		 * auth.inMemoryAuthentication().withUser("admin")
		 * .password("{noop}1234").roles("ADMIN","USER").and().
		 * withUser("user").password("{noop}1234").roles("USER");
		 */

		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);

	}

	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		/*
		 * http.csrf().disable(). // not generate synchronized token
		 * authorizeRequests().antMatchers("/login/**", "/register/**",
		 * "/healthcheck").permitAll().and().
		 * formLogin().and().authorizeRequests().antMatchers(HttpMethod.POST,
		 * "/tasks/**").hasAuthority("ADMIN")
		 * .and().authorizeRequests().anyRequest().authenticated(); //
		 * .and().httpBasic();
		 */

		 
			http.csrf().disable() // we don't care for CSRF in this example	
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			//.and() .formLogin()   
			.and()
			  .authorizeRequests().antMatchers("/register/**", "/login/**", "/healthcheck/**", "/h2-console/**").permitAll()				
			                      .antMatchers("/tasks/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
				.and()
				.addFilter(new JWTAuthenticationFilter(authenticationManager()))
				.addFilterBefore(new JWTAuthorizationFilter(),UsernamePasswordAuthenticationFilter.class );
			
	
	}

	
	
	
	
	/*
	 * @Bean public UserDetailsService userDetailsService() {
	 * 
	 * User.UserBuilder users = User.withDefaultPasswordEncoder();
	 * InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	 * manager.createUser(users.username("user").password("password").roles("USER").
	 * build());
	 * manager.createUser(users.username("admin").password("password").roles("USER",
	 * "ADMIN").build()); return manager;
	 * 
	 * }
	 */
	
	
	

}
