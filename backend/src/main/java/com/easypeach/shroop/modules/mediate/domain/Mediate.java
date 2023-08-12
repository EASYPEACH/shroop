package com.easypeach.shroop.modules.mediate.domain;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.transaction.domain.Transaction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Mediate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(length = 255, nullable = false)
    private String title;

    @Column(length = 255, nullable = false)
    private String content;

    @Column(nullable = false)
    private MediateStatus status;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;
}
