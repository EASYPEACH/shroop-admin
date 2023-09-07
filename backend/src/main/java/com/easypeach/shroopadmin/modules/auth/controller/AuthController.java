package com.easypeach.shroopadmin.modules.auth.controller;

import java.security.Principal;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroopadmin.modules.auth.presentation.AuthenticationPrincipal;
import com.easypeach.shroopadmin.modules.auth.service.JwtProvider;
import com.easypeach.shroopadmin.modules.global.response.BasicResponse;
import com.easypeach.shroopadmin.modules.member.dto.request.MemberSignInRequest;
import com.easypeach.shroopadmin.modules.member.service.MemberService;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	private final MemberService memberService;
	private final JwtProvider jwtProvider;

	@GetMapping("/")
	public ResponseEntity<Boolean> checkLogin(@AuthenticationPrincipal String loginId){
		System.out.println(loginId);
		Boolean isLogin = memberService.checkLogin(loginId);
		return ResponseEntity.ok().body(isLogin);
	}
   
	@PostMapping("/login")
	public ResponseEntity<BasicResponse> signIn(@RequestBody MemberSignInRequest memberSignInRequest, final HttpServletResponse httpServletResponse) {
		String accessToken = memberService.login(memberSignInRequest);
		settingAccessTokenCookie(accessToken, httpServletResponse);
		return ResponseEntity.ok().body(new BasicResponse(accessToken));
	}

	@GetMapping("/logout")
	public ResponseEntity<BasicResponse> signOut(@CookieValue(name = "ACCESS_TOKEN") Cookie accessToken, final HttpServletResponse response){

		accessToken.setValue(null);
		accessToken.setPath("/");
		accessToken.setHttpOnly(true);
		accessToken.setSecure(true);
		response.addCookie(accessToken);

		return ResponseEntity.ok().body(new BasicResponse("로그아웃 되었습니다."));
	}



	private void settingAccessTokenCookie(
		final String accessToken, final HttpServletResponse httpServletResponse) {
		Cookie cookie = new Cookie("ACCESS_TOKEN", accessToken);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		
		httpServletResponse.addCookie(cookie);
	}
	
}
