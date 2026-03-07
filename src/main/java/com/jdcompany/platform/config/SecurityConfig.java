package com.jdcompany.platform.config;

import com.jdcompany.platform.service.OAuth2UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final OAuth2UserService oAuth2UserService;

	public SecurityConfig(OAuth2UserService oAuth2UserService) {
		this.oAuth2UserService = oAuth2UserService;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/", "/login", "/login/**", "/css/**", "/js/**", "/images/**").permitAll()
				.requestMatchers("/api/users/signup").permitAll()
				.requestMatchers("/home", "/home/**").authenticated()
				.anyRequest().authenticated()
			)
			.oauth2Login(oauth2 -> oauth2
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.userInfoEndpoint(userInfo -> userInfo
					.userService(oAuth2UserService)
				)
			)
			.logout(logout -> logout
				.logoutSuccessUrl("/")
			)
			.csrf(csrf -> csrf.disable());
		return http.build();
	}
}
