package com.easypeach.shroop.infra.security.filter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.easypeach.shroop.modules.auth.domain.SecurityMember;
import com.easypeach.shroop.modules.auth.dto.request.SignInRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
	private static final String DEFAULT_LOGIN_REQUEST_URL = "/api/auth/sign-in";
	private static final String HTTP_METHOD = "POST";
	private static final String CONTENT_TYPE = "application/json";
	private static final AntPathRequestMatcher DEFAULT_LOGIN_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(
		DEFAULT_LOGIN_REQUEST_URL, HTTP_METHOD);
	private final ObjectMapper objectMapper;

	public JsonUsernamePasswordAuthenticationFilter(final ObjectMapper objectMapper,
		final AuthenticationSuccessHandler authenticationSuccessHandler,
		final AuthenticationFailureHandler authenticationFailureHandler) {

		super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
		this.objectMapper = objectMapper;
		setAuthenticationSuccessHandler(authenticationSuccessHandler);
		setAuthenticationFailureHandler(authenticationFailureHandler);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
		AuthenticationException,
		IOException,
		ServletException {

		if (request.getContentType() == null || !request.getContentType().equals(CONTENT_TYPE)) {
			throw new AuthenticationServiceException(
				"Authentication Content-Type not supported: " + request.getContentType());
		}

		SignInRequest loginDto = objectMapper.readValue(
			StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8),
			SignInRequest.class);

		UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginDto.getLoginId(), loginDto.getPassword());
		authToken.setDetails(this.authenticationDetailsSource.buildDetails(request));

		return this.getAuthenticationManager().authenticate(authToken);
	}
}
