package com.easypeach.shroop.infra.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import com.easypeach.shroop.modules.auth.exception.InvalidTokenException;
import com.easypeach.shroop.modules.auth.exception.MemberNotExistException;
import com.easypeach.shroop.modules.global.exception.dto.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AuthExceptionHandlerFilter extends OncePerRequestFilter {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (InvalidTokenException | MemberNotExistException ex) {
			response.setStatus(403);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");

			ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
			String result = objectMapper.writeValueAsString(errorResponse);
			response.getWriter().write(result);
		}
	}
}
