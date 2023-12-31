package com.easypeach.shroop.infra.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.easypeach.shroop.modules.global.exception.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException authException) throws IOException, ServletException {

		log.debug("[ SecurityAuthenticationEntryPoint.AuthenticationEntryPoint ]");
		response.setStatus(403);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		ErrorResponse errorResponse = new ErrorResponse("로그인 인증이 필요합니다");
		String result = objectMapper.writeValueAsString(errorResponse);
		response.getWriter().write(result);
	}
}

