package com.easypeach.shroop.infra.s3.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3UploadService {

	private final AmazonS3Client amazonS3Client;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public String saveFile(final MultipartFile multipartFile) throws IOException {
		String originalFilename = multipartFile.getOriginalFilename();

		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(multipartFile.getSize());

		metadata.setContentType(multipartFile.getContentType());

		// 저장
		System.out.println(amazonS3Client.getRegion());

		amazonS3Client.putObject(bucket, originalFilename, multipartFile.getInputStream(), metadata);

		// 저장된 파일의 URL 반환
		return amazonS3Client.getUrl(bucket, originalFilename).toString();
	}

	public ResponseEntity<UrlResource> downloadImage(final String originalFilename) {

		UrlResource urlResource = new UrlResource(amazonS3Client.getUrl(bucket, originalFilename));

		String contentDisposition = "attachment; filename=\"" + originalFilename + "\"";

		// header에 CONTENT_DISPOSITION 설정을 통해 클릭 시 다운로드 진행
		return ResponseEntity.ok()
			.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
			.body(urlResource);

	}

	public void deleteImage(final String originalFilename) {
		amazonS3Client.deleteObject(bucket, originalFilename);
	}
}