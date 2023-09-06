package com.easypeach.shroopadmin.modules.member.dto.response;

import java.time.LocalDateTime;

import com.easypeach.shroopadmin.modules.member.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
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
	private Boolean isDelete;
	private Boolean isBlock;

	public MemberInfo(Long id, String profileImg, String loginId, String nickName, String phoneNumber,
		LocalDateTime createDate ,Long gradeScore,Role role) {
		this.id = id;
		this.profileImg = profileImg;
		this.loginId = loginId;
		this.nickName = nickName;
		this.phoneNumber = phoneNumber;
		this.createDate = createDate;
		this.gradeScore = gradeScore;
		if(role.equals(Role.ROLE_USER)){
			this.isBlock = false;
			this.isDelete = false;
		}else if(role.equals(Role.ROLE_NOT_AUTH_USER)){
			this.isBlock = true;
			this.isDelete = false;
		}else if(role.equals(Role.ROLE_DELETE)){
			this.isBlock = true;
			this.isDelete = true;
		}

	}

	public MemberInfo(Long id, String loginId, String nickname) {
		this.id = id;
		this.loginId = loginId;
		this.nickName = nickname;
	}
}
