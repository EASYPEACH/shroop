package com.easypeach.shroop.modules.mediate.domain;

import java.time.LocalDate;

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

@Table(name = "mediate_img")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MediateImg {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "mediate_id", nullable = false)
	private Mediate mediate;

	@Column(name = "img_url", length = 255, nullable = false)
	private String ImgUrl;

	@Column(name = "create_date")
	@CreatedDate
	private LocalDate createDate;
}
