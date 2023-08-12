package com.easypeach.shroop.modules.product.domain;

import javax.persistence.*;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name = "detailed_conditions", length = 255, nullable = false)
    private String detailedConditions;
}
