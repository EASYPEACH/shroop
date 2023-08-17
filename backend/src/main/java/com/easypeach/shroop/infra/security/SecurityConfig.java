package com.easypeach.shroop.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.easypeach.shroop.infra.security.filter.AuthExceptionHandlerFilter;
import com.easypeach.shroop.infra.security.filter.JwtAuthFilter;
import com.easypeach.shroop.modules.auth.service.JwtTokenProvider;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.httpBasic().disable()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.authorizeRequests()
			.antMatchers("/api/auth/sign-up", "/api/auth/sign-in").permitAll()
			.antMatchers(HttpMethod.GET, "/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.cors()
			.and()
			.addFilterBefore(new JwtAuthFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(new AuthExceptionHandlerFilter(), JwtAuthFilter.class);

		return http.build();
	}

}
