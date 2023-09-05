package com.easypeach.shroopadmin.modules.member.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberInfo {
	private Long id;
	private String profileImg;
	private String loginId;
	private String nickName;
	private String phoneNumber;
	private LocalDateTime createDate;
	private Long gradeScore;

	public MemberInfo(Long id, String profileImg, String loginId, String nickName, String phoneNumber,
		LocalDateTime createDate) {
		this.id = id;
		this.profileImg = profileImg;
		this.loginId = loginId;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.createDate = createDate;
	}

	public MemberInfo(Long id, String loginId, String nickname) {
		this.id = id;
		this.loginId = loginId;
		this.nickName = nickname;
	}
}
