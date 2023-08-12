package com.easypeach.shroop.modules.report.domain;

import com.easypeach.shroop.modules.member.domain.Member;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Todo : member_id FK
    @OneToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "block_date", nullable = false)
    private LocalDate blockDate;


}
