package com.easypeach.shroop.infra.security;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.boot.web.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.easypeach.shroop.infra.security.filter.JsonUsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final ObjectMapper objectMapper;
	private final UserDetailsService userDetailsService;
	private final AuthenticationSuccessHandler loginSuccessHandler;
	private final AuthenticationFailureHandler loginFailureHandler;
	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final PasswordEncoder passwordEncoder;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.httpBasic().disable()
			.csrf().disable()
			.formLogin().disable();

		// http
		// 	.sessionManagement()
		// 	.maximumSessions(1)
		// 	.maxSessionsPreventsLogin(false)
		// 	.and()
		// 	.addObjectPostProcessor(new ObjectPostProcessor<CompositeSessionAuthenticationStrategy>() {
		// 		@Override
		// 		public <O extends CompositeSessionAuthenticationStrategy> O postProcess(O object) {
		// 			CompositeSessionAuthenticationStrategy strategy = (CompositeSessionAuthenticationStrategy)object;
		// 			jsonUsernamePasswordAuthenticationFilter().setSessionAuthenticationStrategy(strategy);
		// 			return object;
		// 		}
		// 	})
		// ;

		http.logout(logout ->
			logout.logoutSuccessUrl("/logout")
				.permitAll()
				.deleteCookies("JSESSIONID")
				.logoutSuccessHandler((request, response, authentication) -> {
					response.setStatus(HttpServletResponse.SC_OK);
				})
				.invalidateHttpSession(true)
				.clearAuthentication(true));

		http
			.authorizeRequests()
			.antMatchers("/api/notifications")
			.hasRole("USER")
			.antMatchers("/api/auth/me", "/api/auth/test",
				"/api/auth/sign-up", "/api/auth/phone/**", "/api/auth/sign-in", "/check/**", "/api/bank/creating")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/**")
			.permitAll()
			.anyRequest()
			.hasRole("USER")
			.and()
			.cors()
			.and()
			.addFilterBefore(jsonUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		http
			.exceptionHandling()
			.authenticationEntryPoint(authenticationEntryPoint);

		return http.build();
	}

	@Bean
	public JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter() {
		JsonUsernamePasswordAuthenticationFilter jsonUsernamePasswordAuthenticationFilter = new JsonUsernamePasswordAuthenticationFilter(
			objectMapper, loginSuccessHandler, loginFailureHandler);
		jsonUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManager());
		return jsonUsernamePasswordAuthenticationFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

		provider.setPasswordEncoder(passwordEncoder);
		provider.setUserDetailsService(userDetailsService);

		return new ProviderManager(provider);
	}

	@Bean
	public TomcatContextCustomizer sameSiteCookiesConfig() {
		return context -> {
			final Rfc6265CookieProcessor cookieProcessor = new Rfc6265CookieProcessor();
			cookieProcessor.setSameSiteCookies(SameSiteCookies.NONE.getValue());
			context.setCookieProcessor(cookieProcessor);
		};
	}
}
