package com.easypeach.shroop.modules.report.controller;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.easypeach.shroop.modules.common.ControllerTest;
import com.easypeach.shroop.modules.report.domain.Report;
import com.easypeach.shroop.modules.report.dto.request.ReportRequest;
import com.easypeach.shroop.modules.report.service.ReportImgService;
import com.easypeach.shroop.modules.report.service.ReportService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(ReportController.class)
class ReportControllerTest extends ControllerTest {

	@MockBean
	private ReportService reportService;

	@MockBean
	private ReportImgService reportImgService;

	@Autowired
	protected ObjectMapper objectMapper;

	@Test
	@DisplayName("신고 및 중재 신청하기 테스트 코드")
	public void saveReportTest() throws Exception {
		Long memberId = 1L;
		String title = "신고 제목";
		String content = "신고 내용";
		boolean isMediate = true;

		ReportRequest reportRequest = new ReportRequest(title, isMediate, content);

		MockMultipartFile file1 = new MockMultipartFile("multipartFileList", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("multipartFileList", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());

		given(reportService.saveReport(memberId, reportRequest)).willReturn(Report.forTestCodeReport());

		String json = objectMapper.writeValueAsString(reportRequest);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/api/reports")
				.file(file1)
				.file(file2)
				.file(new MockMultipartFile("reportRequest", "", "application/json", json.getBytes(StandardCharsets.UTF_8)))
				.contentType(MediaType.MULTIPART_FORM_DATA))
			.andExpect(status().isOk())
			.andDo(document("reports",
				responseFields(
					fieldWithPath("message").description("결과 메세지")
				)))
			.andDo(print());
	}

}