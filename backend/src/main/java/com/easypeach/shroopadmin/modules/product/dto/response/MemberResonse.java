package com.easypeach.shroopadmin.modules.product.dto.response;

import com.easypeach.shroopadmin.modules.member.domain.Member;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberResonse {
	private Long id;

	private String nickName;

	public MemberResonse(Member member) {
		this.id = member.getId();
		this.nickName = member.getNickname();
	}
}
