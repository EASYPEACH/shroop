package com.easypeach.shroop.infra.security.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.easypeach.shroop.infra.aop.log.user.UserLog;
import com.easypeach.shroop.infra.aop.log.user.UserLogRepository;
import com.easypeach.shroop.modules.auth.domain.SecurityMember;
import com.easypeach.shroop.modules.auth.dto.response.LoginFailResponse;
import com.easypeach.shroop.modules.auth.dto.response.LoginSuccessResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.Role;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class SecurityAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	private static Role[] UnAuthorizeAccessList = {Role.ROLE_DELETE, Role.ROLE_NOT_AUTH_USER};
	private final ObjectMapper objectMapper;
	private final UserLogRepository userLogRepository;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		log.debug("[ SecurityAuthenticationSuccessHandler.onAuthenticationSuccess ]");
		response.setStatus(200);
		response.setContentType("application/json");
		response.setCharacterEncoding("utf-8");

		SecurityMember securityMember = (SecurityMember)authentication.getPrincipal();
		Member member = securityMember.getMember();
		if (!checkPermission(response, member)) {
			return;
		}

		LoginSuccessResponse loginDto = new LoginSuccessResponse(member.getLoginId(), member.getNickname());

		UserLog userLog = UserLog.createUserLog(member.getId(), "로그인 성공");
		userLogRepository.save(userLog);

		String loginDtoAsString = objectMapper.writeValueAsString(loginDto);
		response.getWriter().write(loginDtoAsString);
	}

	public boolean checkPermission(HttpServletResponse response, Member member) throws IOException {
		for (Role role : UnAuthorizeAccessList) {
			if (member.getRole().equals(role)) {
				response.setStatus(404);
				response.setContentType("application/json");
				response.setCharacterEncoding("utf-8");
				String message = objectMapper.writeValueAsString(
					new LoginFailResponse(member.getRole().getResponseMessage()));
				response.getWriter().write(message);
				return false;
			}
		}
		return true;
	}
}
