package com.easypeach.shroop.modules.report.domain;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class BlackList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Todo : member_id FK

    @Column(name = "block_date", nullable = false)
    private LocalDate blockDate;


}
