package com.easypeach.shroop.modules.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.infra.s3.service.S3UploadService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.respository.ProductImgRepository;
import com.easypeach.shroop.modules.product.respository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductImgService {
	private final ProductImgRepository productImgRepository;
	private final ProductRepository productRepository;
	private final S3UploadService s3UploadService;

	@Transactional
	public void saveProductImg(List<MultipartFile> productImgList,
		List<MultipartFile> defectImgList, Product product, boolean isDefect) {
		try {
			List<ProductImg> imgList = insertImgList(productImgList, defectImgList, product, isDefect);
			productImgRepository.saveAll(imgList);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Transactional
	public void updateProductImgList(List<MultipartFile> productImgList,
		List<MultipartFile> defectImgList, Long productId, boolean isDefect) {
		Product product = productRepository.findById(productId).get();
		try {
			List<ProductImg> imgList = insertImgList(productImgList, defectImgList, product, isDefect);
			product.getProductImgList().clear();
			for (ProductImg img : imgList) {
				img.setProduct(product);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public List<ProductImg> insertImgList(List<MultipartFile> productImgList, List<MultipartFile> defectImgList,
		Product product, boolean isDefect) throws IOException {
		List<ProductImg> imgList = new ArrayList<>();
		imgList.addAll(createImgList(productImgList, product, false));
		if (isDefect) {
			imgList.addAll(createImgList(defectImgList, product, true));
		}
		return imgList;
	}

	public List<ProductImg> createImgList(List<MultipartFile> requestImgList,
		Product product, boolean isDefect) throws
		IOException {
		List<ProductImg> imgList = new ArrayList<>();
		for (MultipartFile multipartFile : requestImgList) {
			String uploadUrl = s3UploadService.saveFile(multipartFile);
			imgList.add(ProductImg.createProductImg(product, uploadUrl, isDefect));
		}
		return imgList;
	}

}