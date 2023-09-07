package com.easypeach.shroopadmin.modules.member.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignInRequest {
	
	private String loginId;
	private String password;


}
