package com.easypeach.shroop.modules.product.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "product_img")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ProductImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "product_img_url", length = 255, nullable = false)
    private String productImgUrl;

    @Column(name = "is_defect", nullable = false)
    private boolean isDefect;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;

    public static ProductImg createProductImg(
            final Product product,
            final String productImgUrl,
            final boolean isDefect
    ) {
        ProductImg productImg = new ProductImg();
        productImg.product = product;
        productImg.productImgUrl = productImgUrl;
        productImg.isDefect = isDefect;
        return productImg;
    }

}
