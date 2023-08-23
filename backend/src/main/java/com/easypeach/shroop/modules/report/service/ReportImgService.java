package com.easypeach.shroop.modules.report.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.modules.report.domain.Report;
import com.easypeach.shroop.modules.report.domain.ReportImg;
import com.easypeach.shroop.modules.report.domain.ReportImgRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportImgService {

	private final ReportImgRepository reportImgRepository;

	// TODO: S3 연동 후 수정
	// private final S3UploadService s3UploadService;

	@Transactional
	public void saveReportImgs(final Report report, final List<MultipartFile> multipartFileList) {

		List<ReportImg> reportImgList = new ArrayList<>();
		try {
			if (multipartFileList != null && !multipartFileList.isEmpty()) {
				String uploadDir = "src/main/resources/images"; // TODO: S3 연결 후 경로 지정

				File dir = new File(uploadDir);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				for (MultipartFile multipartFile : multipartFileList) {
					// TODO: S3 연동 후 수정
					// String uploadUrl = s3UploadService.saveFile(multipartFile);
					// log.info("s3 반환 URL : " + uploadUrl);

					String fileName = multipartFile.getOriginalFilename();
					Path filePath = Paths.get(uploadDir + File.separator + fileName);

					multipartFile.transferTo(filePath);
					reportImgList.add(ReportImg.createReprotImg(report, filePath.toString()));
				}

				reportImgRepository.saveAll(reportImgList);
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
