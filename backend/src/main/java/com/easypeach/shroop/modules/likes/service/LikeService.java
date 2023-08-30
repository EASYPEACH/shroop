package com.easypeach.shroop.modules.likes.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.likes.domain.LikeRepository;
import com.easypeach.shroop.modules.likes.domain.Likes;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.respository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class LikeService {

	private final LikeRepository likeRepository;
	private final ProductRepository productRepository;
	
	@Transactional
	public Long saveLikes(Member member, Long productId) {
		Product product = productRepository.getById(productId);
		boolean isExist = likeRepository.existsLikesByMemberAndProduct(member, product);
		if (!isExist) {
			Likes like = Likes.createLike(member, product);
			likeRepository.save(like);
		}
		Long totalLikes = likeRepository.countLikesByProduct(product);
		product.setLikesCount(totalLikes);
		return totalLikes;
	}

	@Transactional
	public Long deleteLikes(Member member, Long productId) {
		Product product = productRepository.getById(productId);
		boolean isExist = likeRepository.existsLikesByMemberAndProduct(member, product);
		if (isExist) {
			Likes like = likeRepository.getByMemberAAndProduct(member, product);
			likeRepository.delete(like);
		}
		Long totalLikes = likeRepository.countLikesByProduct(product);
		product.setLikesCount(totalLikes);
		return totalLikes;
	}
}
