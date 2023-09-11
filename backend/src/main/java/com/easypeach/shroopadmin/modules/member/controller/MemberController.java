package com.easypeach.shroopadmin.modules.member.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroopadmin.modules.global.request.SearchRequest;
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
	public ResponseEntity<Page<MemberInfo>> getMembers(final SearchRequest searchRequest, final Pageable pageable) {
		Page<MemberInfo> dtoMembers = memberService.getMemberList(searchRequest, pageable);
		return ResponseEntity.ok(dtoMembers);
	}

	@GetMapping("/{memberId}")
	public ResponseEntity<MemberInfo> getMemberDetails(@PathVariable Long memberId) {
		MemberInfo memberInfo = memberService.getMemberInfo(memberId);
		return ResponseEntity.ok(memberInfo);
	}

	@GetMapping("/{memberId}/log")
	public ResponseEntity<Page<UserLogDto>> getMemberLog(@PathVariable Long memberId, Pageable pageable) {

		Page<UserLog> userLogs = userLogRepository.findLogsByMemberId(memberId, pageable);

		Page<UserLogDto> userLogDtos = userLogs.map(u -> new UserLogDto(
			u.getMemberId(),
			u.getLog(),
			u.getParamName(),
			u.getParamValue(),
			u.getCreatedTime()
		));

		return ResponseEntity.ok(userLogDtos);
	}

	@GetMapping("/{memberId}/edit-form")
	public ResponseEntity<MemberInfo> getEditForm(@PathVariable Long memberId) {
		Member member = memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
		MemberInfo memberInfo = new MemberInfo(member.getId(), member.getLoginId(), member.getNickname());

		return ResponseEntity.ok(memberInfo);
	}

	@PatchMapping("/{memberId}")
	public ResponseEntity<BasicResponse> requestEdit(@PathVariable Long memberId,
		@RequestPart(value = "userImg", required = false) List<MultipartFile> userImgList,
		@RequestPart MemberInfo editRequest) {
		log.info("------ _{} {}", editRequest, userImgList);
		memberService.update(memberId, editRequest, userImgList);
		return ResponseEntity.ok(new BasicResponse("수정 처리가 되었습니다."));
	}

	@DeleteMapping("/{memberId}")
	public ResponseEntity<BasicResponse> deleteMember(@PathVariable Long memberId) {
		String resultMsg = memberService.delete(memberId);
		return ResponseEntity.ok(new BasicResponse(resultMsg));
	}

	@PatchMapping("/{memberId}/role")
	public ResponseEntity<BasicResponse> blockMember(@PathVariable Long memberId) {
		String resultMsg = memberService.changeRole(memberId);
		return ResponseEntity.ok(new BasicResponse(resultMsg));
	}

}
