package com.easypeach.shroop.modules.report.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.auth.support.LoginMember;
import com.easypeach.shroop.modules.global.response.BasicResponse;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.report.domain.Report;
import com.easypeach.shroop.modules.report.dto.request.ReportRequest;
import com.easypeach.shroop.modules.report.service.ReportImgService;
import com.easypeach.shroop.modules.report.service.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/api/reports")
@RestController
@RequiredArgsConstructor
public class ReportController {

	private final ReportService reportService;

	private final ReportImgService reportImgService;

	@PostMapping
	public ResponseEntity<BasicResponse> saveReport(
		@LoginMember Member member,
		@RequestPart(value = "multipartFileList", required = false) List<MultipartFile> multipartFileList,
		@Validated @RequestPart ReportRequest reportRequest
	) {

		// 신고 저장
		Report newReport = reportService.saveReport(member.getId(), reportRequest);

		// 이미지 저장
		reportImgService.saveReportImgs(newReport, multipartFileList);

		return ResponseEntity.status(HttpStatus.OK).body(new BasicResponse("신고가 완료되었습니다."));

	}

}
