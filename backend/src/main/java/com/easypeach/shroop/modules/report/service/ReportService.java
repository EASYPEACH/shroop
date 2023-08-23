package com.easypeach.shroop.modules.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.report.domain.Report;
import com.easypeach.shroop.modules.report.domain.ReportRepository;
import com.easypeach.shroop.modules.report.domain.ReportStatus;
import com.easypeach.shroop.modules.report.dto.request.ReportRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportService {

	private final ReportRepository reportRepository;

	// TODO: MemberService로 변경할 예정
	private final MemberRepository memberRepository;

	// TODO: Product 매핑
	// private final ProductService productService;

	@Transactional
	public Report saveReport(final Long memberId, final ReportRequest reportRequest) {
		// TODO: Product 매핑
		// Product product = productService.findId(reportRequest.getProductId());

		String title = reportRequest.getTitle();
		String content = reportRequest.getContent();
		boolean isMediate = reportRequest.isMediate();
		log.info("isMediate : " + isMediate);
		// TODO: MemberService로 변경되면 수정해야 함
		Member member = memberRepository.findById(memberId).get();

		Report report = Report.createReport(member, title, content, isMediate, ReportStatus.READY);

		return reportRepository.save(report);
	}
}

