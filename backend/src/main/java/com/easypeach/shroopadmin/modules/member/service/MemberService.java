package com.easypeach.shroopadmin.modules.member.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.member.domain.MemberRepository;
import com.easypeach.shroopadmin.modules.member.domain.Role;
import com.easypeach.shroopadmin.modules.member.dto.response.MemberInfo;
import com.easypeach.shroopadmin.modules.secession.BlackMember;
import com.easypeach.shroopadmin.modules.secession.BlackMemberRepository;

import lombok.RequiredArgsConstructor;

@Transactional
@RequiredArgsConstructor
@Service
public class MemberService {
	private final MemberRepository memberRepository;
	private final BlackMemberRepository blackMemberRepository;

	public Page<MemberInfo> getMemberList(Pageable pageable){
		Page<Member> members = memberRepository.findAll(pageable);
		//Page로 되어 있는 엔티티 dto로 변경
		Page<MemberInfo> dtoMembers = members.map(m -> new MemberInfo(m.getId(),
			m.getLoginId(),m.getNickname(),m.getPhoneNumber(),m.getCreateDate()));

		return dtoMembers;
	}

	public MemberInfo getMemberInfo(Long memberId){
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalStateException::new);
		MemberInfo memberInfo = new MemberInfo(
			member.getId(),
			member.getLoginId(),
			member.getNickname(),
			member.getPhoneNumber(),
			member.getCreateDate(),
			member.getGradeScore());

		return memberInfo;
	}

	public Member update(Long memberId, MemberInfo memberInfo){
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
		member.updateLoginId(memberInfo.getLoginId());
		member.updateNickname(memberInfo.getNickname());
		return member;
	}

	public BlackMember delete(Long memberId){
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException :: new);

		BlackMember blackMember = BlackMember.from(member);
		blackMemberRepository.save(blackMember);
		memberRepository.delete(member);
		return blackMember;
	}

	public Member removeRole(Long memberId){
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException :: new);
		member.updateRole(Role.ROLE_NOT_AUTH_USER);
		return member;
	}

}
