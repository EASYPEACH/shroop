package com.easypeach.shroop.modules.likes.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;

import lombok.Getter;
import lombok.ToString;

@Table(name = "likes",
	uniqueConstraints = {
		@UniqueConstraint(
			name = "memberIdAndProductId",
			columnNames = {
				"member_id",
				"product_id"
			}
		)
	}
)
@Entity
@Getter
@ToString
public class Likes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Version
	private Long version;

	public static Likes createLike(Member member, Product product) {
		Likes like = new Likes();
		like.member = member;
		like.product = product;
		return like;
	}
}
