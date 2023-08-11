package com.easypeach.shroop.modules.notification.domain;

import javax.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;



    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String link;

    @Column(length = 255, nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean checked;




}
