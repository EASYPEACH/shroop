package com.easypeach.shroop.modules.product.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductReturn;
import com.easypeach.shroop.modules.product.dto.request.ProductReturnRequest;
import com.easypeach.shroop.modules.product.respository.ProductReturnRepository;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import com.easypeach.shroop.modules.transaction.domain.TransactionStatus;
import com.easypeach.shroop.modules.transaction.service.TransactionService;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductReturnService {

	private final ProductReturnRepository productReturnRepository;

	private final ProductService productService;

	private final ProductReturnImgService productReturnImgService;

	private final TransactionService transactionService;

	@Transactional
	public void saveProductReturn(final Long memberId, final Long productId,
		final ProductReturnRequest productReturnRequest,
		final List<MultipartFile> productReturnImgList) {
		Product product = productService.findByProductId(productId);
		ProductReturn productReturn = ProductReturn.createProductReturn(product, productReturnRequest.getContent());
		ProductReturn newProductReturn = productReturnRepository.save(productReturn);

		Transaction transaction = transactionService.findByProductId(productId);
		transaction.updateStatus(TransactionStatus.RETURN_REQUEST);
		
		productReturnImgService.saveProductReturnImg(newProductReturn, productReturnImgList);

	}
}
