package com.easypeach.shroop.infra.security.handler;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import com.easypeach.shroop.modules.auth.domain.SecurityMember;
import com.easypeach.shroop.modules.auth.dto.response.LoginSuccessResponse;
import com.easypeach.shroop.modules.global.exception.dto.ErrorResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		log.debug("[ SecurityAuthenticationSuccessHandler.onAuthenticationSuccess ]");
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		SecurityMember securityMember = (SecurityMember) authentication.getPrincipal();
		Member member = securityMember.getMember();
		LoginSuccessResponse loginDto = new LoginSuccessResponse(member.getLoginId(),member.getNickname());

		String loginDtoAsString = objectMapper.writeValueAsString(loginDto);
		response.getWriter().write(loginDtoAsString);
	}
}
