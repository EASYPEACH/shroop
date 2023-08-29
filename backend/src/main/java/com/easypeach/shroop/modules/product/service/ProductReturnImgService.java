package com.easypeach.shroop.modules.product.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.infra.s3.service.S3UploadService;
import com.easypeach.shroop.modules.product.domain.ProductReturn;
import com.easypeach.shroop.modules.product.domain.ProductReturnImg;
import com.easypeach.shroop.modules.product.respository.ProductReturnImgRepository;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductReturnImgService {

	private final ProductReturnImgRepository productReturnImgRepository;

	private final S3UploadService s3UploadService;

	@Transactional
	public void saveProductReturnImg(final ProductReturn productReturn,
		final List<MultipartFile> productReturnImgList) {

		List<ProductReturnImg> imgList = new ArrayList<>();
		try {
			if (productReturnImgList != null && !productReturnImgList.isEmpty()) {
				for (MultipartFile productReturnImg : productReturnImgList) {
					String uploadUrl = s3UploadService.saveFile(productReturnImg);

					imgList.add(ProductReturnImg.createProductReturnImg(productReturn, uploadUrl));
				}

				productReturnImgRepository.saveAll(imgList);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

}
