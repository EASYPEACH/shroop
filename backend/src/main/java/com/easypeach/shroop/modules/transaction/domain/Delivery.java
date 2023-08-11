package com.easypeach.shroop.modules.transaction.domain;

import javax.persistence.*;

@Entity
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tracking_number", unique = true, nullable = false)
    private Long trackingNumber;

    @Column(name = "location", length = 255, nullable = false)
    private String location;
}
