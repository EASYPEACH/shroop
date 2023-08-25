package com.easypeach.shroop.modules.product.domain;

import lombok.Getter;

@Getter
public enum ProductGrade {
    UPPER("상"), MIDDLE("중"), LOWER("하");
    private final String grade;

    ProductGrade(String grade) {
        this.grade = grade;
    }

}
