package com.easypeach.shroop.modules.likes.domain;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.easypeach.shroop.modules.likes.exception.LikesProductNotFoundException;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;

public interface LikeRepository extends JpaRepository<Likes, Long> {
	Long countLikesByProduct(Product product);

	List<Likes> findAllByMember(Member member);

	boolean existsLikesByMemberAndProduct(Member member, Product product);

	Optional<Likes> findByMemberAndProduct(Member member, Product product);

	void deleteAllByProduct(Product product);

	default Likes getByMemberAAndProduct(final Member member, final Product product) {
		return findByMemberAndProduct(member, product).orElseThrow(
			() -> new LikesProductNotFoundException("좋아요 기록이 없습니다"));
	}

	@Query(value = "select likes from Likes likes where likes.member = :member",
		countQuery = "select count(likes.member) from Likes likes where likes.member = :member")
	Page<Likes> findLikesPage(@Param(value = "member") Member member, Pageable pageable);
}
