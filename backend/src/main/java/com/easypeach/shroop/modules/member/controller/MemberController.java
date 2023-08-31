package com.easypeach.shroop.modules.member.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.dto.reponse.ProfileEditForm;
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

	@GetMapping("/profile")
	public ResponseEntity<ProfileEditForm> findProfileEditPage(@LoginMember Member member) {
		ProfileEditForm profileEditForm = memberService.findProfile(member.getId());
		return ResponseEntity.ok(profileEditForm);
	}

	@PatchMapping("/profile")
	public ResponseEntity<BasicResponse> updateProfile(@LoginMember Member member
		, @RequestPart @Validated ProfileEditRequest editRequest,
		@RequestPart List<MultipartFile> userImg) {

		memberService.updateProfile(member.getId(), userImg, editRequest);
		return ResponseEntity.ok(new BasicResponse("수정을 완료하였습니다"));
	}

}
