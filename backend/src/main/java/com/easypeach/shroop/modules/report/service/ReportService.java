package com.easypeach.shroop.modules.report.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.service.ProductService;
import com.easypeach.shroop.modules.report.domain.Report;
import com.easypeach.shroop.modules.report.domain.ReportRepository;
import com.easypeach.shroop.modules.report.domain.ReportStatus;
import com.easypeach.shroop.modules.report.dto.request.ReportRequest;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportService {

	private final ReportRepository reportRepository;

	private final MemberService memberService;

	private final ProductService productService;

	private final TransactionService transactionService;

	@Transactional
	public Report saveReport(final Long memberId, final ReportRequest reportRequest) {

		Product product = productService.findByProductId(reportRequest.getProductId());

		String title = reportRequest.getTitle();
		String content = reportRequest.getContent();
		boolean isMediate = reportRequest.getIsMediate();
		Long reporteeId;
		if (product.getSeller().getId() == memberId) {
			Transaction transaction = transactionService.findByProductId(reportRequest.getProductId());
			reporteeId = transaction.getBuyer().getId();
		} else {
			reporteeId = product.getSeller().getId();
		}

		Member reporter = memberService.findById(memberId);
		Member reportee = memberService.findById(reporteeId);

		Report report = Report.createReport(reporter, reportee, product, title, content, isMediate,
			ReportStatus.REPORT_REQUEST);

		if (isMediate) {
			product.getTransaction().updateStatus(TransactionStatus.MEDIATE_REQUEST);
		}

		return reportRepository.save(report);
	}
}

