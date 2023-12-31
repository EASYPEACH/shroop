package com.easypeach.shroop.modules.product.respository;

import static com.easypeach.shroop.modules.likes.domain.QLikes.*;
import static com.easypeach.shroop.modules.product.domain.QProduct.*;
import static com.easypeach.shroop.modules.transaction.domain.QTransaction.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.easypeach.shroop.modules.likes.domain.QLikes;
import com.easypeach.shroop.modules.member.domain.Role;
import com.easypeach.shroop.modules.product.domain.QProductImg;
import com.easypeach.shroop.modules.product.dto.response.ProductOneImgResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public Page<ProductOneImgResponse> searchProduct(Long memberId, String title, Long categoryId,
		boolean hasNotTransaction,
		Pageable pageable) {

		QProductImg productImg = QProductImg.productImg;
		QProductImg subProductImg = new QProductImg("subProductImg");

		List<ProductOneImgResponse> content = queryFactory
			.select(Projections.constructor(ProductOneImgResponse.class,
				product.id,
				transaction.status,
				product.title,
				product.category.name,
				productImg.productImgUrl,
				product.likesCount,
				product.price,
				product.isCheckedDeliveryFee,
				product.content,
				product.createDate,
				likesISNull(likes, memberId)
			)).distinct()
			.from(product)
			.leftJoin(transaction).on(transaction.product.id.eq(product.id))
			.join(productImg).on(productImg.product.id.eq(product.id))
			.leftJoin(likes).on(likes.product.id.eq(product.id), likesISNull(likes, memberId))
			.where(titleContains(title), categoryIdEq(categoryId), hasNotTransactionIsNull(hasNotTransaction),
				productImg.id.eq(
					JPAExpressions
						.select(subProductImg.id.min())
						.from(subProductImg)
						.where(subProductImg.product.id.eq(product.id))
				),
				product.seller.role.eq(Role.ROLE_USER).or(product.seller.role.eq(Role.ROLE_ADMIN))
			)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.orderBy(product.createDate.desc())
			.fetch();

		Long count = queryFactory
			.select(product.count()).distinct()
			.from(product)
			.leftJoin(transaction).on(transaction.product.id.eq(product.id))
			.join(productImg).on(productImg.product.id.eq(product.id))
			// .leftJoin(likes).on(likes.product.id.eq(product.id))
			.where(titleContains(title), categoryIdEq(categoryId), hasNotTransactionIsNull(hasNotTransaction),
				productImg.id.eq(
					JPAExpressions
						.select(subProductImg.id.min())
						.from(subProductImg)
						.where(subProductImg.product.id.eq(product.id))
				),
				product.seller.role.eq(Role.ROLE_USER).or(product.seller.role.eq(Role.ROLE_ADMIN))
			)
			.fetchOne();

		return new PageImpl<>(content, pageable, count);
	}

	private BooleanExpression titleContains(String title) {
		if (title == null || title.isBlank()) {
			return null;
		}
		return product.title.contains(title);
	}

	private BooleanExpression categoryIdEq(Long categoryId) {
		if (categoryId == null || categoryId == 0L) {
			return null;
		}

		return product.category.id.eq(categoryId);
	}

	private BooleanExpression hasNotTransactionIsNull(boolean hasNotTransaction) {
		if (!hasNotTransaction) {
			return null;
		}

		return transaction.status.isNull();
	}

	private BooleanExpression likesISNull(QLikes like, Long memberId) {
		if (like == null || memberId == null) {
			return likes.id.eq(0L);
		}

		return likes.member.id.eq(memberId);
	}

}
