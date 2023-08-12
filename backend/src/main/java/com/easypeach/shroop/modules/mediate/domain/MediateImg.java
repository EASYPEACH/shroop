package com.easypeach.shroop.modules.mediate.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "mediate_img")
@Entity
@EntityListeners(AuditingEntityListener.class)
public class MediateImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mediate_id", nullable = false)
    private Mediate mediate;

    @Column(name = "img_url", length = 255, nullable = false)
    private String ImgUrl;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;
}
