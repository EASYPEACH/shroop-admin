package com.easypeach.shroopadmin.modules.member.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
	private Long id;
	private String loginId;
	private String nickname;
	private String phoneNumber;
	private LocalDateTime createdDate;
	private Long gradeScore;

	public MemberInfo(Long id,String loginId,String nickname,String phoneNumber,LocalDateTime createdDate){
		this.id = id;
		this.loginId = loginId;
		this.nickname = nickname;
		this.phoneNumber = phoneNumber;
		this.createdDate = createdDate;
	}

	public MemberInfo(Long id,String loginId, String nickname){
		this.id = id;
		this.loginId = loginId;
		this.nickname = nickname;
	}
}
