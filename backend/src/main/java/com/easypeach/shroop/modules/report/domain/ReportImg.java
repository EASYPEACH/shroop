package com.easypeach.shroop.modules.report.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Table(name = "report_img")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ReportImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "report_id", nullable = false)
	private Report report;

	@Column(name = "img_url", length = 255, nullable = false)
	private String imgUrl;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDateTime createDate;

	public static ReportImg createReprotImg(
		final Report report,
		final String imgUrl
	) {
		ReportImg reportImg = new ReportImg();
		reportImg.report = report;
		reportImg.imgUrl = imgUrl;

		return reportImg;
	}

}
