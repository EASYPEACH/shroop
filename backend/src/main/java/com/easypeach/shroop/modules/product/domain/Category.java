package com.easypeach.shroop.modules.product.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, nullable = false)
	private String name;

	// TODO: 상세조건 내용 추후 협의
	//    @Column(name = "detailed_conditions", length = 255)
	//    private String detailedConditions;

	public Category(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static Category createCategory(final String name) {
		Category category = new Category();
		category.name = name;
		return category;
	}

}
