package com.easypeach.shroop.modules.auth.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.member.dto.reponse.LikeProductInfo;
import com.easypeach.shroop.modules.member.dto.reponse.MyPageInfoResponse;
import com.easypeach.shroop.modules.member.dto.reponse.ProfileEditForm;
import com.easypeach.shroop.modules.member.dto.request.MemberInfo;
import com.easypeach.shroop.modules.member.dto.request.ProfileEditRequest;

public class MemberControllerTest extends ControllerTest {

	@MockBean
	Page page;

	@DisplayName("마이페이지 이동 시 유저 정보 불러오기")
	@Test
	void getMemberInfo() throws Exception {

		// given
		LikeProductInfo likeProductInfo = new LikeProductInfo(
			1L,
			"productImgUrl",
			"title",
			"1,000",
			new LikeProductInfo.CategoryInfo(1L, "전자제품"),
			LocalDateTime.now());
		ArrayList<LikeProductInfo> list = new ArrayList<>();
		list.add(likeProductInfo);
		Page<LikeProductInfo> page = new PageImpl<>(list);

		MyPageInfoResponse info = new MyPageInfoResponse(
			"loginId",
			"userImg",
			"nickname",
			100L,
			"50811112222",
			100L,
			page);

		given(memberService.getMyInfo(any(), any())).willReturn(info);
		PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "id"));

		// when & then
		mockMvc.perform(get("/api/members/me")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.param("page", String.valueOf(pageable.getPageNumber()))
				.param("size", String.valueOf(pageable.getPageSize()))
				.param("sort", pageable.getSort().toString())
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
			.andDo(print())
			.andDo(document("members/getMyInfo",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("members/getMyInfo",
				responseFields(
					fieldWithPath("userImg").description("유저이미지 링크"),
					fieldWithPath("loginId").description("로그인 아이디"),
					fieldWithPath("nickname").description("닉네임"),
					fieldWithPath("point").description("포인트"),
					fieldWithPath("account").description("계좌번호"),
					fieldWithPath("gradeScore").description("등급 점수"),
					fieldWithPath("page.content").description("좋아요 게시글 리스트"),
					fieldWithPath("page.content[].id").description("상품 UID"),
					fieldWithPath("page.content[].productImgUrl").description("상품 이미지"),
					fieldWithPath("page.content[].title").description("상품 제목"),
					fieldWithPath("page.content[].price").description("상품 가격"),
					fieldWithPath("page.content[].categoryInfo.id").description("카테고리 UID"),
					fieldWithPath("page.content[].categoryInfo.name").description("카테고리 이름"),
					fieldWithPath("page.content[].createDate").description("상품 게시 날짜"),
					fieldWithPath("page.pageable").description("페이지 정보"),
					fieldWithPath("page.last").description("마지막 페이지 여부"),
					fieldWithPath("page.totalPages").description("총 페이지 개수"),
					fieldWithPath("page.totalElements").description("총 요소 개수"),
					fieldWithPath("page.size").description("페이지 크기"),
					fieldWithPath("page.number").description("현재 페이지"),
					fieldWithPath("page.sort").description("정렬 정보"),
					fieldWithPath("page.sort.empty").description("정렬 유무"),
					fieldWithPath("page.sort.sorted").description(""),
					fieldWithPath("page.sort.unsorted").description(""),
					fieldWithPath("page.first").description("첫 페이지 여부"),
					fieldWithPath("page.numberOfElements").description("페이지 요소 개수"),
					fieldWithPath("page.empty").description("")
				)))
			.andReturn();
	}

	@DisplayName("개인정보변경 화면 이동시 프로필 정보 불러오기")
	@Test
	void getProfile() throws Exception {
		ProfileEditForm editForm = new ProfileEditForm("loginId", "nickname", "01012341234", "profileImg");

		// given
		given(memberService.findProfile(any())).willReturn(editForm);

		// when & then
		mockMvc.perform(get("/api/members/profile")
				.accept(MediaType.APPLICATION_JSON)
				.characterEncoding(StandardCharsets.UTF_8)
				.contentType(MediaType.APPLICATION_JSON)
				.content(""))
			.andDo(print())
			.andDo(document("members/getProfile",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("members/getProfile",
				responseFields(
					fieldWithPath("loginId").description("로그인 아이디"),
					fieldWithPath("nickname").description("닉네임"),
					fieldWithPath("phoneNumber").description("휴대전화번호"),
					fieldWithPath("profileImg").description("프로필 이미지")
				)))
			.andReturn();
	}

	@DisplayName("프로필 수정 요청")
	@Test
	void fetchProfile() throws Exception {
		//given
		ProfileEditRequest editForm = new ProfileEditRequest(
			"nickname",
			"oldPassword",
			"newPassword",
			1L,
			"01012341234",
			"1234");

		String editFormToJson = objectMapper.writeValueAsString(editForm);

		MockMultipartFile file1 = new MockMultipartFile("userImg", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		doNothing().when(memberService).updateProfile(any(), any(), any());

		// when & then
		mockMvc.perform(
				multipart("/api/members/profile")
					.file(file1)
					.file(
						new MockMultipartFile("editRequest", "", "application/json",
							editFormToJson.getBytes(StandardCharsets.UTF_8)))
					.contentType(MediaType.MULTIPART_FORM_DATA).with(request -> {
						request.setMethod("PATCH");
						return request;
					})
			)
			.andDo(print())
			.andDo(document("members/patchProfile",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("members/patchProfile",
				requestParts(
					partWithName("editRequest").description("편집할 회원 정보"),
					partWithName("userImg").description("유저 이미지")
				),
				requestPartFields("editRequest",
					fieldWithPath("nickname").description("닉네임"),
					fieldWithPath("oldPassword").description("이전 패스워드"),
					fieldWithPath("newPassword").description("새로운 패스워드"),
					fieldWithPath("uuid").description("인증번호 요청 시 받은 uuid"),
					fieldWithPath("phoneNumber").description("휴대전화번호"),
					fieldWithPath("phoneAuthNumber").description("인증번호")
				),
				responseFields(
					fieldWithPath("message").description("수정을 완료하였습니다")
				)))
			.andReturn();
	}

	@DisplayName("탈퇴 요청")
	@Test
	void cancel_memberShip() throws Exception {
		//given
		MemberInfo memberInfo = new MemberInfo("password");

		doNothing().when(memberService).delete(any(), any());

		// when & then
		mockMvc.perform(
				delete("/api/members/leave")
					.accept(MediaType.APPLICATION_JSON)
					.characterEncoding(StandardCharsets.UTF_8)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(memberInfo)))
			.andDo(print())
			.andDo(document("members/leave",
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint())))
			.andExpect(status().isOk())
			.andDo(document("members/leave",
				requestFields(
					fieldWithPath("password").description("패스워드")
				),
				responseFields(
					fieldWithPath("message").description("탈퇴를 완료하였습니다")
				)))
			.andReturn();
	}

}
