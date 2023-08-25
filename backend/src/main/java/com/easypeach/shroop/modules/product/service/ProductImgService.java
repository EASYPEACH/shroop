package com.easypeach.shroop.modules.product.service;

import com.easypeach.shroop.infra.s3.service.S3UploadService;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.product.domain.ProductImg;
import com.easypeach.shroop.modules.product.respository.ProductImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductImgService {
    private final ProductImgRepository productImgRepository;
    private final S3UploadService s3UploadService;
    
    @Transactional
    public void saveProductImg(final Product product, final List<MultipartFile> productImgList) {
        List<ProductImg> imgList = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : productImgList) {
                String uploadUrl = s3UploadService.saveFile(multipartFile);
                imgList.add(ProductImg.createProductImg(product, uploadUrl, product.isDefect()));
            }
            productImgRepository.saveAll(imgList);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
