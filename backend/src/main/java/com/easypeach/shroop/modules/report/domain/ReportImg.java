package com.easypeach.shroop.modules.report.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity(name = "report_img")
@EntityListeners(AuditingEntityListener.class)
public class ReportImg {

    @Id
    private Long id;

    //Todo: report_id FK
    @Column(name = "img_url", length = 255, nullable = false)
    private String ImgUrl;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;



}
