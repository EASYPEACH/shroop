package com.easypeach.shroop.modules.report.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(length = 255, nullable = false)
	private String title;

	@Column(length = 255, nullable = false)
	private String content;

	@Column(nullable = false)
	private boolean isMediate;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private ReportStatus status;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static Report createReport(
		final Member member,
		final Product product,
		final String title,
		final String content,
		final boolean isMediate,
		final ReportStatus status
	) {
		Report report = new Report();
		report.member = member;
		report.product = product;
		report.title = title;
		report.content = content;
		report.isMediate = isMediate;
		report.status = status;

		return report;
	}

	public static Report forTestCodeReport() {
		return new Report();
	}

}
