package com.easypeach.shroop.modules.product.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

// TODO: 상세조건 내용 추후 협의
//    @Column(name = "detailed_conditions", length = 255)
//    private String detailedConditions;

    public static Category createCategory(final String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }
}
