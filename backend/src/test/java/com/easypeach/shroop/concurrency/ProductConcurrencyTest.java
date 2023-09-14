package com.easypeach.shroop.concurrency;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.likes.service.LikeService;
import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.member.service.MemberService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductGrade;
import com.easypeach.shroop.modules.product.dto.request.ProductRequest;
import com.easypeach.shroop.modules.product.respository.ProductRepository;
import com.easypeach.shroop.modules.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ProductConcurrencyTest {
	@Autowired
	private MemberService memberService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductService productService;
	@PersistenceContext
	private EntityManager entityManager;

	private Long productId;

	@DisplayName("좋아요 동시성 테스트")
	@Test
	void LikeConcurrencyTest() throws InterruptedException {
		Member member1 = memberService.findByNickname("test111111");
		Member member2 = memberService.findByNickname("test222222");
		productId = saveProduct(member1.getId());
		ExecutorService executorService = Executors.newFixedThreadPool(2);
		CountDownLatch latch = new CountDownLatch(2);

		Product product = productRepository.getById(productId);

		executorService.execute(() -> {
			try {
				likeService.saveLikes(member1, productId); // 멤버1이 좋아요 누른다
			} catch (Exception ex) {
				log.error("Catch Transaction conflict", ex);
			}
			latch.countDown();
		});

		executorService.execute(() -> {
			try {
				likeService.saveLikes(member2, productId); // 멤버2이 좋아요 누른다
			} catch (Exception ex) {
				log.error("Catch Transaction conflict", ex);
			}
			latch.countDown();
		});
		latch.await();
		Product findProduct = productRepository.getById(productId);

		// 동시에 눌러도 좋아요는 2개가 업되어야 한다
		Assertions.assertThat(findProduct.getLikesCount()).isEqualTo(2);
		productRepository.delete(product);

	}

	Long saveProduct(Long memberId) {
		ProductRequest productRequest = new ProductRequest(
			"아이패드 5",
			1L,
			"애플",
			20000L,
			false,
			LocalDate.parse("2023-05-05"),
			ProductGrade.LOWER,
			false,
			"안써서 팔아요. 안써서 팔아요",
			"대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자대충30자"
		);

		List<MultipartFile> defectImgList = new ArrayList<>();
		List<MultipartFile> productImgList = new ArrayList<>();
		MockMultipartFile file1 = new MockMultipartFile("productImgList", "test1.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test1 image".getBytes());
		MockMultipartFile file2 = new MockMultipartFile("defectImgList", "test2.jpg", MediaType.IMAGE_JPEG_VALUE,
			"test2 image".getBytes());

		productImgList.add(file1);
		productImgList.add(file2);

		Long productId = productService.saveProduct(memberId, productRequest, productImgList, defectImgList);

		return productId;
	}

}
