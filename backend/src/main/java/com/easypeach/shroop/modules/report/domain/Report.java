package com.easypeach.shroop.modules.report.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Todd
//    @Column(name = "reporter_id", nullable = false, unique = true)
//    private Long reporterId;
   //Todo
//    @Column(name = "product_id", length = 50, nullable = false, unique = true)
//    private Long productId;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String content;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReportStatus role;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;



}
