package com.easypeach.shroop.modules.product.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class ProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: porduct_id

    @Column(name = "img_url", length = 255, nullable = false)
    private String imgUrl;

    @Column(name = "defect_img", nullable = false)
    private boolean defectImg;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;

}
