package com.easypeach.shroop.modules.product.domain;

import com.easypeach.shroop.modules.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Member seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private Member buyer;

    @Column(length = 100, nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "product_grade", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ProductGrade productGrade;

    @OneToMany(mappedBy = "productImgUrl", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImg> productImgList;

    @Column(length = 50, nullable = false)
    private String brand;

    @Column(nullable = false)
    private Long price;

    @Column(name = "is_checked_delivery_fee", nullable = false)
    private boolean isCheckedDeliveryFee;

    @Column(length = 255, nullable = false)
    private String content;

    @Column(name = "purchase_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate purchaseDate;

    @Column(name = "is_defect", nullable = false)
    private boolean isDefect;

    @Column(name = "sale_reason", length = 255, nullable = false)
    private String saleReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProductStatus productStatus;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @Column(name = "update_date")
    @LastModifiedDate
    private LocalDateTime updateDate;

    public static Product createProduct(
            final Member seller,
            final String title,
            final ProductStatus productStatus,
            final Category category,
            final String brand,
            final Long price,
            final boolean isCheckedDeliveryFee,
            final LocalDate purchaseDate,
            final ProductGrade productGrade,
            final boolean isDefect,
            final String saleReason,
            final String content
    ) {
        Product product = new Product();

        product.seller = seller;
        product.title = title;
        product.productStatus = productStatus;
        product.category = category;
        product.purchaseDate = purchaseDate;
        product.productGrade = productGrade;
        product.brand = brand;
        product.price = price;
        product.isCheckedDeliveryFee = isCheckedDeliveryFee;
        product.isDefect = isDefect;
        product.saleReason = saleReason;
        product.content = content;

        return product;
    }

    public static Product forTestCodeProduct() {
        return new Product();
    }

    public void setBuyer(Member buyer) {
        this.buyer = buyer;
    }

    public void setProductStatus(ProductStatus productStatus) {
        this.productStatus = productStatus;
    }


}
