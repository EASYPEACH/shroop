package com.easypeach.shroop.modules.notification.domain;

import com.easypeach.shroop.modules.member.domain.Member;

import javax.persistence.*;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String link;

    @Column(length = 255, nullable = false)
    private String message;

    @Column(nullable = false)
    private boolean checked;




}
