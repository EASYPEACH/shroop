package com.easypeach.shroop.modules.auth.service;

import com.easypeach.shroop.modules.auth.exception.InvalidTokenException;
import com.easypeach.shroop.modules.member.domain.Role;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final long accessTokenValidTime = 1800000L ;
    private final UserDetailsService userDetailsService;

    public String generateAccessToken(String loginId,String nickname, Role role){
        return createToken(loginId,nickname,role);
    }

    public String createToken(String loginId,String nickname, Role role){
        Claims claims = Jwts.claims().setSubject(loginId);
        claims.put("nickname",nickname);
        claims.put("role",role);

        Date nowDate = new Date();
        Long expirationDate = nowDate.getTime() + accessTokenValidTime;

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(new Date(expirationDate))
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
        return token;
    }

    public Authentication getAuthentication(String token){
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails,"",userDetails.getAuthorities());
    }

    public String getUserId(String token){
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
    public boolean validateToken(String jwtToken){
        try {
            log.info("jwtToken "+jwtToken);
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return !claims.getBody().getExpiration().before(new Date());
        }catch (JwtException | IllegalArgumentException e){
            throw new InvalidTokenException("유효하지 않은 토큰입니다");
        }
    }
}