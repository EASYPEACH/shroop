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
import com.easypeach.shroop.modules.product.exception.ProductImgLengthException;
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
    public void saveProductImg(final List<MultipartFile> productImgList,
                               final List<MultipartFile> defectImgList, final Long productId, final boolean isDefect) {

        List<ProductImg> imgList = insertImgList(productImgList, defectImgList, isDefect);
        productImgRepository.saveAll(imgList);
    }

    @Transactional
    public void updateProductImgList(final List<MultipartFile> productImgList,
                                     final List<MultipartFile> defectImgList, final Long productId, final boolean isDefect) {
        Product product = productRepository.getById(productId);

            List<ProductImg> imgList = insertImgList(productImgList, defectImgList, isDefect);
            product.getProductImgList().clear();
            for (ProductImg img : imgList) {
                img.setProduct(product);
            }

    }

    public List<ProductImg> insertImgList(final List<MultipartFile> productImgList,
                                          final List<MultipartFile> defectImgList, final boolean isDefect) {
        List<ProductImg> imgList = new ArrayList<>();
        imgList.addAll(createImgList(productImgList, false));
        if (isDefect) {
            imgList.addAll(createImgList(defectImgList, true));
        }
        return imgList;
    }

    public List<ProductImg> createImgList(final List<MultipartFile> requestImgList, final boolean isDefect) {
        List<ProductImg> imgList = new ArrayList<>();
        for (MultipartFile multipartFile : requestImgList) {
            String uploadUrl = s3UploadService.saveFile(multipartFile);
            imgList.add(ProductImg.createProductImg(uploadUrl, isDefect));
        }
        return imgList;
    }

    public void checkImgLength(final List<MultipartFile> productImgList) {
        if (productImgList.size() < 2) {
            throw new ProductImgLengthException("사진은 2장이상 등록해주세요");
        }

    }
}
