package com.easypeach.shroop.modules.likes.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.likes.service.LikeService;
import com.easypeach.shroop.modules.member.domain.Member;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/likes")
@RestController
@RequiredArgsConstructor
public class LikeController {

	private final LikeService likeService;

	@PostMapping("/{productId}")
	public ResponseEntity<Long> saveLikes(@LoginMember Member member, @PathVariable Long productId) {
		return ResponseEntity.status(HttpStatus.OK).body(likeService.saveLikes(member, productId));
	}

	@DeleteMapping("/{productId}")
	public void deleteLikes(@LoginMember Member member, @PathVariable Long productId) {
		likeService.deleteLikes(member, productId);
	}

}
