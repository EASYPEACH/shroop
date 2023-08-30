package com.easypeach.shroop.modules.likes.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easypeach.shroop.modules.likes.exception.LikesProductNotFoundException;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;

public interface LikeRepository extends JpaRepository<Likes, Long> {
	Long countLikesByProduct(Product product);

	List<Likes> findAllByMember(Member member);

	Optional<Likes> findByMember(Member member);

	boolean existsLikesByMemberAndProduct(Member member, Product product);

	Optional<Likes> findByMemberAndProduct(Member member, Product product);

	default Likes getByMember(final Member member) {
		return findByMember(member).orElseThrow(() -> new LikesProductNotFoundException("좋아요 기록이 없습니다"));
	}

	default Likes getByMemberAAndProduct(final Member member, final Product product) {
		return findByMemberAndProduct(member, product).orElseThrow(
			() -> new LikesProductNotFoundException("좋아요 기록이 없습니다"));
	}
}
