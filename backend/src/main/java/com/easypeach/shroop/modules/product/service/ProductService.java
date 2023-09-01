package com.easypeach.shroop.modules.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import com.easypeach.shroop.modules.product.dto.response.ProductOneImgResponse;
import com.easypeach.shroop.modules.product.dto.response.ProductResponse;
import com.easypeach.shroop.modules.product.dto.response.SearchProductResponse;
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

	public List<ProductResponse> findAll(final Member member) {
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

	public ProductResponse getProductInfo(final Member member, final Long productId) {
		Product product = productRepository.getById(productId);
		ProductResponse productResponse = setProductResponse(product);
		boolean isLikesProduct = likeRepository.existsLikesByMemberAndProduct(member, product);
		if (isLikesProduct) {
			productResponse.setIsLike();
		}
		return productResponse;
	}

	public Product findByProductId(final Long productId) {
		Product product = productRepository.getById(productId);
		return product;
	}

	public ProductImg getProductImg(final Product product) {
		Product findProduct = productRepository.getById(product.getId());
		findProduct.getProductImgList().get(0).getId();
		return findProduct.getProductImgList().get(0);
	}

	@Transactional
	public Product saveProduct(final Long memberId, final ProductRequest productRequest) {
		Member seller = memberRepository.getById(memberId);
		Category category = categoryRepository.getById(productRequest.getCategoryId());
		Product product = Product.createProduct(seller, productRequest, category);
		return productRepository.save(product);
	}

	@Transactional
	public Product updateProduct(final Long memberId, final Long productId, final ProductRequest productRequest
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
	public void deleteProduct(final Long memberId, final Long productId) {
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

	public ProductResponse setProductResponse(final Product product) {
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

	public SearchProductResponse searchProduct(final Member member,
		final String title, final Long categoryId, final Boolean hasTransaction,
		final Pageable pageable) {

		Page<ProductOneImgResponse> productPage;
		if (member == null) {
			productPage = productRepository.searchProduct(null, title, categoryId,
				hasTransaction,
				pageable);

		} else {
			productPage = productRepository.searchProduct(member.getId(), title, categoryId,
				hasTransaction,
				pageable);
		}

		int pageCount = productPage.getTotalPages();

		List<ProductOneImgResponse> list = productPage.stream().collect(Collectors.toList());

		return new SearchProductResponse(pageCount, list);
	}

}
