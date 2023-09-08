package com.easypeach.shroop.modules.report.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.easypeach.shroop.infra.s3.service.S3UploadService;
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

    private final S3UploadService s3UploadService;

    @Transactional
    public void saveReportImgs(final Report report, final List<MultipartFile> multipartFileList) {

        List<ReportImg> reportImgList = new ArrayList<>();
        if (multipartFileList != null && !multipartFileList.isEmpty()) {

            for (MultipartFile multipartFile : multipartFileList) {

                String uploadUrl = s3UploadService.saveFile(multipartFile);

                reportImgList.add(ReportImg.createReprotImg(report, uploadUrl));
            }

            reportImgRepository.saveAll(reportImgList);
        }


    }
}
