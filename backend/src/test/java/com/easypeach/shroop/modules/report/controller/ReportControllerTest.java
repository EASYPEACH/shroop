package com.easypeach.shroop.modules.report.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.auth.domain.SecurityMember;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.Role;
import com.easypeach.shroop.modules.report.domain.Report;
import com.easypeach.shroop.modules.report.dto.request.ReportRequest;
import com.easypeach.shroop.modules.report.service.ReportImgService;
import com.easypeach.shroop.modules.report.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureRestDocs
@MockBean(JpaMetamodelMappingContext.class)
@WebMvcTest(ReportController.class)
class ReportControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReportService reportService;

	@MockBean
	private ReportImgService reportImgService;

	@Autowired
	protected ObjectMapper objectMapper;

	public Member member;

	@BeforeEach
	void setUp() {
		UserDetails user = createUserDetails();
		SecurityContext context = SecurityContextHolder.getContext();
		context.setAuthentication(
			new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities()));
	}

	private UserDetails createUserDetails() {
		member = Member.createMember(
			"abc12345",
			"abc12345",
			"abc12345",
			"01011112222",
			Role.ROLE_USER,
			0L
		);
		SecurityMember securityMember = new SecurityMember(member);
		return securityMember;
	}

	@Test
	@DisplayName("신고 및 중재 신청하기 테스트 코드")
	@WithMockUser(username = "abc12345")
	public void saveReportTest() throws Exception {
		Long memberId = 1L;
		String title = "Test title";
		String content = "Test Content";
		boolean isMediate = true;

		ReportRequest reportRequest = new ReportRequest(title, isMediate, content);

		// 파일 업로드를 위한 MockMultipartFile 리스트 생성
		MockMultipartFile file1 = new MockMultipartFile("multipartFileList1", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("multipartFileList2", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());

		when(reportService.saveReport(memberId, reportRequest)).thenReturn(Report.forTestCodeReport());

		String json = objectMapper.writeValueAsString(reportRequest);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/reports")
				.file(file1)
				.file(file2)
				.file(new MockMultipartFile("reportRequest", "", "application/json", json.getBytes(StandardCharsets.UTF_8)))
				.with(SecurityMockMvcRequestPostProcessors.csrf())
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(document("reports",
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());
	}

}