package com.easypeach.shroopadmin.modules.auth.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import com.easypeach.shroopadmin.modules.auth.exception.NotAuthrizedUserException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

	private final JwtProvider jwtProvider;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws
		Exception {

		Cookie[] cookies = request.getCookies();

		if (cookies == null) {
			throw new NotAuthrizedUserException("로그인이 필요합니다.");
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("ACCESS_TOKEN")) {
				String accessToken = cookie.getValue();
				jwtProvider.validateToken(accessToken);
				return true;
			}
		}
		return false;

	}
}

