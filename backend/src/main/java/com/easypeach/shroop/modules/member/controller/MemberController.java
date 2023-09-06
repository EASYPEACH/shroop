package com.easypeach.shroop.modules.member.controller;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.infra.aop.log.user.UserTrace;
import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.dto.reponse.MyPageInfoResponse;
import com.easypeach.shroop.modules.member.dto.reponse.ProfileEditForm;
import com.easypeach.shroop.modules.member.dto.request.MemberInfo;
import com.easypeach.shroop.modules.member.dto.request.ProfileEditRequest;
import com.easypeach.shroop.modules.member.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/members")
@RestController
@RequiredArgsConstructor
public class MemberController {
	private final MemberService memberService;

	@UserTrace(value = "마이페이지 이동")
	@GetMapping("/me")
	public ResponseEntity<MyPageInfoResponse> findMyPage(@LoginMember final Member member, Pageable pageable){
		MyPageInfoResponse myInfo = memberService.getMyInfo(member.getId(),pageable);

		return ResponseEntity.ok(myInfo);
	}

	@GetMapping("/profile")
	public ResponseEntity<ProfileEditForm> findProfileEditPage(@LoginMember final Member member) {
		ProfileEditForm profileEditForm = memberService.findProfile(member.getId());
		return ResponseEntity.ok(profileEditForm);
	}

	@PatchMapping("/profile")
	public ResponseEntity<BasicResponse> updateProfile(@LoginMember final Member member
		, @RequestPart @Validated final ProfileEditRequest editRequest,
		@RequestPart final List<MultipartFile> userImg) {

		memberService.updateProfile(member.getId(), userImg, editRequest);
		return ResponseEntity.ok(new BasicResponse("수정을 완료하였습니다"));
	}

	@DeleteMapping("/leave")
	public ResponseEntity<BasicResponse> deleteMember(
		@LoginMember final Member member,
		@RequestBody final MemberInfo memberInfo){
		memberService.delete(member.getId(),memberInfo);
		return ResponseEntity.ok(new BasicResponse("탈퇴를 완료하였습니다"));
	}

}
