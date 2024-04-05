package com.pta.configurations;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.pta.entities.Account;
import com.pta.service.AccountService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration {

	@Autowired
	private AccountService accountService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.cors(cor -> cor.disable()).csrf(cs -> cs.disable()).authorizeHttpRequests(auth -> {
			auth.requestMatchers("/", "/**", "/accessDenied")
					.permitAll()
					.requestMatchers("/superadmin/account/index").hasAnyRole("SUPER_ADMIN")
					.requestMatchers("/admin/**").hasAnyRole("SUPER_ADMIN", "ADMIN")
					.requestMatchers("/user/**", "/").hasAnyRole("SUPER_ADMIN", "ADMIN", "MEMBER")
					.anyRequest()
					.authenticated();
		}).formLogin(formLogin -> {
			formLogin.loginPage("/admin/auth/login").loginProcessingUrl("/admin/auth/process-login")
					.usernameParameter("email").passwordParameter("password")
//										.defaultSuccessUrl("/")
					.successHandler(new AuthenticationSuccessHandler() {
						@Override
						public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
								Authentication authentication) throws IOException, ServletException {

							 redirectBasedOnRoles(response, authentication); // Tra ve duong dan theo role
						}
					}).failureUrl("/admin/auth/login?error");
		}).logout(logout -> {
			logout.logoutUrl("/admin/auth/logout").logoutSuccessUrl("/admin/auth/login?logout");
		}).exceptionHandling(ex -> {
			ex.accessDeniedPage("/accessDenied");
		}).build();

	}
	
	private void redirectBasedOnRoles(HttpServletResponse response, Authentication authentication) throws IOException {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String userEmail = userDetails.getUsername();

		Account account = accountService.findByEmail(userEmail);

		if (account != null && account.getStatus().equals(1)) {
			Collection<GrantedAuthority> roles = (Collection<GrantedAuthority>) authentication
					.getAuthorities();

			Map<String, String> urls = new HashMap<String, String>();
			urls.put("ROLE_SUPER_ADMIN", "/admin/account/index");
			urls.put("ROLE_ADMIN", "/admin/account/index");
			urls.put("ROLE_MEMBER", "/");

			String url = "";
			for (GrantedAuthority role : roles) {
				if (urls.containsKey(role.getAuthority())) {
					url = urls.get(role.getAuthority());
					break;
				}
			}
			response.sendRedirect(url);

		} else {
			// Account not found or inactive, redirect to login with error message
			response.sendRedirect("/admin/auth/login?error=inactive");
		}
	}
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(accountService);
	}

	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

}
