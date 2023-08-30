package com.easypeach.shroop.modules.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easypeach.shroop.modules.likes.domain.LikeRepository;
import com.easypeach.shroop.modules.likes.domain.Likes;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.domain.MemberRepository;
import com.easypeach.shroop.modules.product.domain.Category;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.dto.response.MemberResonse;
import com.easypeach.shroop.modules.product.dto.response.ProductImgResponse;
import com.easypeach.shroop.modules.product.dto.response.ProductResponse;
import com.easypeach.shroop.modules.product.exception.ProductException;
import com.easypeach.shroop.modules.product.respository.CategoryRepository;
import com.easypeach.shroop.modules.product.respository.ProductImgRepository;
import com.easypeach.shroop.modules.product.respository.ProductRepository;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class ProductService {
	private final ProductRepository productRepository;
	private final MemberRepository memberRepository;
	private final CategoryRepository categoryRepository;
	private final ProductImgRepository productImgRepository;
	private final TransactionRepository transactionRepository;
	private final LikeRepository likeRepository;

	public List<ProductResponse> findAll(Member member) {
		List<Product> productList = productRepository.findAll();
		List<ProductResponse> productResponsesList = new ArrayList<>();
		List<Likes> likeList = new ArrayList<>();
		if (member != null) {
			likeList = likeRepository.findAllByMember(member);
		}

		for (Product product : productList) {
			productResponsesList.add(setProductResponse(product));
		}

		for (Likes likes : likeList) {
			for (ProductResponse productResponse : productResponsesList) {
				if (likes.getProduct().getId() == productResponse.getId()) {
					productResponse.setIsLike();
				}
			}
		}

		return productResponsesList;
	}

	public ProductResponse getProductInfo(Member member, Long productId) {
		Product product = productRepository.getById(productId);
		ProductResponse productResponse = setProductResponse(product);
		boolean isLikesProduct = likeRepository.existsLikesByMemberAndProduct(member, product);
		if (isLikesProduct) {
			productResponse.setIsLike();
		}
		return productResponse;
	}

	public Product findByProductId(Long productId) {
		Product product = productRepository.getById(productId);
		return product;
	}

	public ProductImg getProductImg(Product product) {
		Product findProduct = productRepository.getById(product.getId());
		findProduct.getProductImgList().get(0).getId();
		return findProduct.getProductImgList().get(0);
	}

	@Transactional
	public Product saveProduct(Long memberId, ProductRequest productRequest) {
		Member seller = memberRepository.getById(memberId);
		Category category = categoryRepository.getById(productRequest.getCategoryId());
		Product product = Product.createProduct(seller, productRequest, category);
		return productRepository.save(product);
	}

	@Transactional
	public Product updateProduct(Long memberId, Long productId, ProductRequest productRequest
	) {
		Product product = productRepository.getById(productId);
		Member loginMember = memberRepository.getById(memberId);
		Member productOwnerMember = memberRepository.getById(product.getSeller().getId());

		if (loginMember != productOwnerMember) {
			throw ProductException.notAuthorizationToUpdate();
		}

		Category category = categoryRepository.getById(productRequest.getCategoryId());
		product.updateProduct(productRequest, category);

		return product;
	}

	@Transactional
	public void deleteProduct(Long memberId, Long productId) {
		Product product = productRepository.getById(productId);
		Member loginMember = memberRepository.getById(memberId);
		Member productOwnerMember = memberRepository.getById(memberId);
		likeRepository.deleteAllByProduct(product);
		if (product.getTransaction() != null) {
			throw ProductException.notStatusDelete(product.getTransaction().getStatus());
		}
		if (loginMember != productOwnerMember) {
			throw ProductException.notAuthorizationToDelete();
		}
		productRepository.delete(product);
	}

	public ProductResponse setProductResponse(Product product) {
		ProductResponse productResponse = new ProductResponse(product);
		MemberResonse seller = new MemberResonse(product.getSeller());
		List<ProductImgResponse> productImgList = productImgRepository.findAllByProduct(product)
			.stream()
			.map(ProductImgResponse::new)
			.collect(Collectors.toList());

		productResponse.setProductImgList(productImgList);
		productResponse.setSeller(seller);
		Transaction transaction = transactionRepository.findByProduct(product);
		if (transaction != null) {
			productResponse.setTransaction(transaction.getStatus());
		}

		return productResponse;
	}

}
