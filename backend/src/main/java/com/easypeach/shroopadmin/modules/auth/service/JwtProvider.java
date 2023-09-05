package com.easypeach.shroopadmin.modules.auth.service;

import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.easypeach.shroopadmin.modules.auth.exception.InvalidTokenException;
import com.easypeach.shroopadmin.modules.auth.exception.NotAuthrizedUserException;
import com.easypeach.shroopadmin.modules.member.domain.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class JwtProvider {

	private byte[] secret;
	private Key key;
	@Value("${spring.jwt.access-token.exp}")
	private String expireDate;

	public JwtProvider(@Value("${spring.jwt.secret}") String secret) {
		this.secret = secret.getBytes();
		this.key = Keys.hmacShaKeyFor(this.secret);
	}

	public String createAccessToken(String loginId, Role role) {
		return createToken(loginId, role, getExpireDateAccessToken());
	}

	public String createToken(String loginId, Role role, Date expireDate) {
		if (role != Role.ROLE_ADMIN) {
			throw new NotAuthrizedUserException("로그인 권한이 없습니다");
		}
		Claims claims = Jwts.claims().setSubject(loginId);
		claims.put("role", role);

		return Jwts.builder()
			.setClaims(claims)
			.setExpiration(expireDate)
			.signWith(key)
			.compact();
	}

	public Claims getClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(token)
			.getBody();
	}

	public boolean validateToken(String accessToken) {
		try {
			Jws<Claims> claimsJws = Jwts.parser()
				.setSigningKey(key)
				.parseClaimsJws(accessToken);
			return !claimsJws.getBody().getExpiration().before(new Date());
		} catch (JwtException | IllegalArgumentException e) {
			throw new InvalidTokenException("유효하지 않는 토큰 입니다");
		}
	}

	public Date getExpireDateAccessToken() {
		long expireTime = 1000 * 60 * 60;
		return new Date(System.currentTimeMillis() + expireTime);
	}

}
