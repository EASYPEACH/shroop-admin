package com.easypeach.shroopadmin.modules.member.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.Response;
import com.easypeach.shroopadmin.modules.global.response.BasicResponse;
import com.easypeach.shroopadmin.modules.log.UserLog;
import com.easypeach.shroopadmin.modules.log.UserLogRepository;
import com.easypeach.shroopadmin.modules.member.domain.Member;
import com.easypeach.shroopadmin.modules.member.domain.MemberRepository;
import com.easypeach.shroopadmin.modules.member.dto.response.MemberInfo;
import com.easypeach.shroopadmin.modules.member.dto.response.UserLogDto;
import com.easypeach.shroopadmin.modules.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value = "/api/members")
@RestController
public class MemberController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final UserLogRepository userLogRepository;

	@GetMapping
	public ResponseEntity<Page<MemberInfo>> getMembers(Pageable pageable) {
		Page<MemberInfo> dtoMembers = memberService.getMemberList(pageable);
		return ResponseEntity.ok(dtoMembers);
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberInfo> getMemberDetails(@PathVariable Long memberId) {
		MemberInfo memberInfo = memberService.getMemberInfo(memberId);
		return ResponseEntity.ok(memberInfo);
	}

	@GetMapping("/{memberId}/log")
	public ResponseEntity<List<UserLogDto>> getMemberLog(@PathVariable Long memberId){

		List<UserLog> userLogs = userLogRepository.findLogsByMemberId(memberId);

		List<UserLogDto> userLogDtos = userLogs.stream().map(u -> new UserLogDto(
			u.getMemberId(),
			u.getLog(),
			u.getParamName(),
			u.getParamValue(),
			u.getCreatedTime()
		)).collect(Collectors.toList());

		return ResponseEntity.ok(userLogDtos);
	}

	@GetMapping("/{memberId}/edit-form")
	public ResponseEntity<MemberInfo> getEditForm(@PathVariable Long memberId){
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
		MemberInfo memberInfo = new MemberInfo(member.getId(),member.getLoginId(), member.getNickname());

		return ResponseEntity.ok(memberInfo);
	}

	@PatchMapping("/{memberId}")
	public ResponseEntity<BasicResponse> requestEdit(@PathVariable Long memberId, @RequestBody MemberInfo form){
		memberService.update(memberId, form);
		return ResponseEntity.ok(new BasicResponse("수정 처리가 되었습니다."));
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<BasicResponse> requestDelete(@PathVariable Long memberId){
		memberService.delete(memberId);
		return ResponseEntity.ok(new BasicResponse("탈퇴 처리가 되었습니다."));
	}

	@PatchMapping("/{memberId}/role")
	public ResponseEntity<BasicResponse> updateRole(@PathVariable Long memberId){
		memberService.removeRole(memberId);
		return ResponseEntity.ok(new BasicResponse("차단 처리가 되었습니다."));
	}

}
