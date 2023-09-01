package com.easypeach.shroop.modules.member.service;

import java.io.IOException;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.infra.s3.service.S3UploadService;
import com.easypeach.shroop.modules.auth.dto.request.PhoneAuthRequest;
import com.easypeach.shroop.modules.auth.service.PhoneAuthService;
import com.easypeach.shroop.modules.likes.domain.Likes;
import com.easypeach.shroop.modules.likes.service.LikeService;
import com.easypeach.shroop.modules.member.domain.DuplicateCheckType;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.member.dto.reponse.LikeProductInfo;
import com.easypeach.shroop.modules.member.dto.reponse.MyPageInfoResponse;
import com.easypeach.shroop.modules.member.dto.reponse.ProfileEditForm;
import com.easypeach.shroop.modules.member.dto.request.ProfileEditRequest;
import com.easypeach.shroop.modules.member.exception.DuplicateValueException;
import com.easypeach.shroop.modules.member.exception.MemberNotExistException;
import com.easypeach.shroop.modules.member.exception.MinusPointException;
import com.easypeach.shroop.modules.member.exception.PasswordNotMatchException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;
	private final PhoneAuthService phoneAuthService;
	private final PasswordEncoder passwordEncoder;
	private final S3UploadService s3UploadService;
	private final LikeService likeService;

	public MyPageInfoResponse getMyInfo(final Long memberId, Pageable pageable) {
		Member findMember = memberRepository.getById(memberId);
		Page<Likes> likedList = likeService.getLikesPage(findMember, pageable);

		Page<LikeProductInfo> likedProductList = likedList.map(likes -> new LikeProductInfo(likes));

		MyPageInfoResponse myPageInfo = new MyPageInfoResponse(
			findMember.getProfileImg(),
			findMember.getNickname(),
			findMember.getPoint(),
			likedProductList
		);

		return myPageInfo;
	}

	public ProfileEditForm findProfile(final Long memberId) {
		Member findMember = memberRepository.getById(memberId);
		return new ProfileEditForm(findMember.getNickname(), findMember.getPhoneNumber(), findMember.getProfileImg());
	}

	@Transactional
	public void updateProfile(final Long memberId, List<MultipartFile> profileImg, ProfileEditRequest req) {
		Member findMember = memberRepository.getById(memberId);
		PhoneAuthRequest phoneAuthRequest = new PhoneAuthRequest(
			req.getUuid(),
			req.getPhoneNumber(),
			req.getPhoneAuthNumber()
		);
		updateImgUrl(findMember, profileImg);
		updateNickname(findMember, req.getNickname());
		updatePassword(findMember, req.getOldPassword(), req.getNewPassword());
		updatePhoneNumber(findMember, phoneAuthRequest);
	}

	public void updateImgUrl(final Member member, final List<MultipartFile> profileImg) {
		if (profileImg.size() == 0) {
			return;
		}
		try {
			String imgUrl = s3UploadService.saveFile(profileImg.get(0));
			member.updateProfileImg(imgUrl);
		} catch (IOException ex) {
			log.error(ex.getMessage());
		}
	}

	public void updateNickname(final Member member, final String newNickname) {
		if (member.getNickname().equals(newNickname)) {
			//닉네임이 같으면 변경하지 않음
			return;
		}
		if (existsByNickname(newNickname)) {
			throw new DuplicateValueException("중복된 닉네임 입니다");
		}
		member.updateNickname(newNickname);
	}

	public void updatePassword(final Member member, final String oldPassword, final String newPassword) {
		if (!(oldPassword.equals("") && newPassword.equals(""))) {
			if (!passwordEncoder.matches(oldPassword, member.getPassword())) {
				throw new PasswordNotMatchException("기존 비밀번호가 맞지 않습니다");
			}

			if (!newPassword.matches("^.*(?=^.{8,30}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$")) {
				throw new PasswordNotMatchException("비밀번호는 영문,숫자,특수문자를 포함 8자이상 입력해주세요");
			}
			member.updatePassword(passwordEncoder.encode(newPassword));
		}
	}

	public void updatePhoneNumber(final Member member, final PhoneAuthRequest phoneAuthRequest) {
		if (phoneAuthRequest.getPhoneNumber().equals(member.getPhoneNumber())) {
			//폰 번호 같으면 검사하지 않음
			return;
		}

		phoneAuthService.checkPhoneAuthNumber(phoneAuthRequest);
	}

	public Member findById(final Long memberId) {
		return memberRepository.findById(memberId)
			.orElseThrow(() -> new MemberNotExistException("회원이 존재하지 않습니다"));
	}

	boolean existsByLoginId(final String loginId) {
		return memberRepository.existsByLoginId(loginId);
	}

	boolean existsByNickname(final String nickname) {
		return memberRepository.existsByNickname(nickname);
	}

	public Member findByLoginId(final String loginId) {
		return memberRepository.findByLoginId(loginId)
			.orElseThrow(() -> new MemberNotExistException("회원이 존재하지 않습니다"));
	}

	public Member findByNickname(final String nickname) {
		return memberRepository.findByNickname(nickname)
			.orElseThrow(() -> new MemberNotExistException("회원이 존재하지 않습니다"));
	}

	public Member findByPhoneNumber(final String phoneNumber) {
		return memberRepository.findByPhoneNumber(phoneNumber)
			.orElseThrow(() -> new MemberNotExistException("회원이 존재하지 않습니다"));
	}

	public boolean getDuplicateResult(final String value, final DuplicateCheckType type) {
		boolean result = false;
		if (type.equals(DuplicateCheckType.LOGIN_ID)) {
			result = memberRepository.findByLoginId(value).isPresent();
		}
		if (type.equals(DuplicateCheckType.NICK_NAME)) {
			result = memberRepository.findByNickname(value).isPresent();
		}
		if (type.equals(DuplicateCheckType.PHONE_NUMBER)) {
			result = memberRepository.findByPhoneNumber(value).isPresent();
		}
		return result;
	}

	@Transactional
	public Long plusPoint(final Long point, final Member member) {
		Member foundMember = memberRepository.getById(member.getId());
		foundMember.addPoint(point);
		return foundMember.getPoint();
	}

	@Transactional
	public Long subtractPoint(final Long point, final Member member) {
		Member foundMember = memberRepository.getById(member.getId());
		if (member.getPoint() >= point) {
			foundMember.subtractPoint(point);
		} else {
			throw new MinusPointException("보유한 포인트보다 환전하려는 포인트가 더 큽니다. 다시 입력해주세요");
		}
		return foundMember.getPoint();
	}
}
