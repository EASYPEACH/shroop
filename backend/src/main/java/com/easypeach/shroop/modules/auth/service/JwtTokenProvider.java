package com.easypeach.shroop.modules.auth.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.easypeach.shroop.modules.auth.exception.InvalidTokenException;
import com.easypeach.shroop.modules.member.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

	private static final String BEARER = "Bearer ";
	private static final String AUTHORIZATION_HEADER = "Authorization";
	private static final String ROLE = "role";
	private static final String NICKNAME = "nickname";

	@Value("${spring.jwt.secret}")
	private String secretKey;

	@Value("${spring.jwt.access-token.exp}")
	private long accessTokenValidTime;
	private final UserDetailsService userDetailsService;

	public String generateAccessToken(String loginId, String nickname, Role role) {
		return createToken(loginId, nickname, role);
	}

	public String createToken(String loginId, String nickname, Role role) {
		Claims claims = Jwts.claims().setSubject(loginId);
		claims.put(NICKNAME, nickname);
		claims.put(ROLE, role);

		Date nowDate = new Date();
		long expirationDate = nowDate.getTime() + accessTokenValidTime;

		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(nowDate)
			.setExpiration(new Date(expirationDate))
			.signWith(SignatureAlgorithm.HS256, secretKey)
			.compact();
	}

	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	public String getUserId(String token) {
		return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER)) {
			return bearerToken.substring(BEARER.length());
		}
		return null;
	}

	public boolean isValidToken(String jwtToken) {
		try {
			log.info("jwtToken: {} ", jwtToken);
			return !Jwts.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(jwtToken)
				.getBody()
				.getExpiration()
				.before(new Date());
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException("유효하지 않은 토큰입니다");
		}
	}

}
