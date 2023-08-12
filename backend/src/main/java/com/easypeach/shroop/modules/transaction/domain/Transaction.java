package com.easypeach.shroop.modules.transaction.domain;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;
import com.easypeach.shroop.modules.report.domain.Report;
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


    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Member buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Member seller;


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    @OneToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @Column(nullable = false)
    private TransactionStatus status;

    @Column(name = "create_date")
    @CreatedDate
    private LocalDate createDate;

}
