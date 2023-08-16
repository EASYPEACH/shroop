package com.easypeach.shroop.modules.report.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
	private Long id;

	//Todo: report_id FK
	@ManyToOne
	@JoinColumn(name = "report_id", nullable = false)
	private Report report;
	@Column(name = "img_url", length = 255, nullable = false)
	private String ImgUrl;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDate createDate;

}
