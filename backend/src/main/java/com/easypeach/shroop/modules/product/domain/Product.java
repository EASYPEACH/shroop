package com.easypeach.shroop.modules.product.domain;

import com.easypeach.shroop.modules.member.domain.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // TODO: saller_id

    // TODO: category_id

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Column(length = 50, nullable = false)
    private String brand;

    @Column(nullable = false)
    private int price;

    @Column(name = "is_checked_delivery_fee",nullable = false)
    private boolean isCheckedDeliveryFee;

    @Column(length = 255, nullable = false)
    private String content;

    @Column(name = "purchase_date")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDate purchaseDate;

    @Column(name = "is_defect", nullable = false)
    private boolean isDefect;

    @Column(name = "sale_reason", length = 255, nullable = false)
    private String saleReason;

    @Column(name = "status", nullable = false)
    private ProductStatus productStatus;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;

    @Column(name = "update_date")
    @LastModifiedDate
    private LocalDate updateDate;

}
