package com.easypeach.shroopadmin.modules.auth.presentation;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.easypeach.shroopadmin.modules.auth.exception.NotAuthrizedUserException;

public class AuthorizationExtractor {

	public static String extract(final HttpServletRequest request){
		Cookie[] cookies =  request.getCookies();
		if(cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("ACCESS_TOKEN")) {
					String accessToken = cookie.getValue();
					if(accessToken != "") {
						return accessToken;
					}
				}
			}
		}

		throw new NotAuthrizedUserException("로그인이 필요합니다.[로그인 체크]");
	}
}
