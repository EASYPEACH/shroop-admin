package com.easypeach.shroopadmin.modules.member.service;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import com.easypeach.shroopadmin.modules.auth.exception.NotAuthrizedUserException;
import com.easypeach.shroopadmin.modules.auth.service.JwtProvider;

import com.easypeach.shroopadmin.infra.s3.service.S3UploadService;

import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.member.domain.MemberRepository;
import com.easypeach.shroopadmin.modules.member.domain.Role;
import com.easypeach.shroopadmin.modules.member.dto.request.MemberSignInRequest;
import com.easypeach.shroopadmin.modules.member.dto.response.MemberInfo;

import com.easypeach.shroopadmin.modules.member.exception.UnAthoriazedException;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService {
	private final MemberRepository memberRepository;

	private final JwtProvider jwtProvider;
	private final PasswordEncoder passwordEncoder;

	private final S3UploadService s3UploadService;


	public Page<MemberInfo> getMemberList(Pageable pageable) {
		Page<Member> members = memberRepository.findAll(pageable);
		//Page로 되어 있는 엔티티 dto로 변경

		Page<MemberInfo> dtoMembers = members.map(m -> new MemberInfo(
			m.getId(),
			m.getProfileImg(),
			m.getLoginId(),
			m.getNickname(),
			m.getPhoneNumber(),
			m.getCreateDate(),
			m.getGradeScore(),
			m.getRole()));


		return dtoMembers;
	}

	public MemberInfo getMemberInfo(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalStateException::new);
		MemberInfo memberInfo = new MemberInfo(
			member.getId(),
			member.getProfileImg(),
			member.getLoginId(),
			member.getNickname(),
			member.getPhoneNumber(),
			member.getCreateDate(),
			member.getGradeScore(),
			member.getRole());

		return memberInfo;
	}

	public Member update(Long memberId, MemberInfo memberInfo, List<MultipartFile> userImgList) {

		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
		member.updateLoginId(memberInfo.getLoginId());
		member.updateNickname(memberInfo.getNickName());
		String imgUrl = "";
		if (userImgList!= null) {
			imgUrl = s3UploadService.saveFile(userImgList.get(userImgList.size()-1));
			member.updateProfileImg(imgUrl);
		}

		return member;
	}


	public String delete(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
		if(!member.getRole().equals(Role.ROLE_DELETE)){
			member.updateRole(Role.ROLE_DELETE);
			return "탈퇴 처리가 완료되었습니다";
		}else{
			member.updateRole(Role.ROLE_USER);
			return "탈퇴 복구가 완료되었습니다";
		}
	}

	public String changeRole(Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
		if(!member.getRole().equals(Role.ROLE_NOT_AUTH_USER)) {
			member.updateRole(Role.ROLE_NOT_AUTH_USER);
			return "차단 처리가 완료되었습니다";
		}else{
			member.updateRole(Role.ROLE_USER);
			return "차단 해제가 완료되었습니다";
		}

	}

	public String login(MemberSignInRequest memberSignInRequest) {
		Member member = memberRepository.getByLoginId(memberSignInRequest.getLoginId());
		boolean isMatchPassword = passwordEncoder.matches(memberSignInRequest.getPassword(), member.getPassword());
		if (!isMatchPassword) {
			throw new UnAthoriazedException("비밀번호가 일치하지 않습니다");
		}

		return jwtProvider.createAccessToken(member.getLoginId(), member.getRole());
	}

	public Boolean checkLogin(String loginId) {
		if (loginId == null){
			System.out.println("탓니?");
			throw new NotAuthrizedUserException("로그인이 필요합니다.");
		}
		return true;
	}
}
