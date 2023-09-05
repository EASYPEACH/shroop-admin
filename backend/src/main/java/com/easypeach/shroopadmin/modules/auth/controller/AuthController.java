package com.easypeach.shroopadmin.modules.auth.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroopadmin.modules.auth.service.JwtProvider;
import com.easypeach.shroopadmin.modules.global.response.BasicResponse;
import com.easypeach.shroopadmin.modules.member.dto.request.MemberSignInRequest;
import com.easypeach.shroopadmin.modules.member.service.MemberService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final MemberService memberService;
	private final JwtProvider jwtProvider;
   
	@PostMapping
	public ResponseEntity<BasicResponse> signIn(@RequestBody MemberSignInRequest memberSignInRequest, final HttpServletResponse httpServletResponse) {
		String accessToken = memberService.login(memberSignInRequest);
		settingAccessTokenCookie(accessToken, httpServletResponse);
		return ResponseEntity.ok().body(new BasicResponse(accessToken));
	}



	private void settingAccessTokenCookie(
		final String accessToken, final HttpServletResponse httpServletResponse) {
		Cookie cookie = new Cookie("ACCESS_TOKEN", accessToken);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(false);
		
		httpServletResponse.addCookie(cookie);
	}
	
}
