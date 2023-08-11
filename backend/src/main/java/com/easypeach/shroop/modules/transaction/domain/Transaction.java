package com.easypeach.shroop.modules.transaction.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Todo : buyer_id

    //Todo : seller_id

    //Todo : product_id

    //Todo : delivery_id

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;

}
