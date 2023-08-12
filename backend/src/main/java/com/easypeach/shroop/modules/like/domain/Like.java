package com.easypeach.shroop.modules.like.domain;

import com.easypeach.shroop.modules.member.domain.Member;
import com.easypeach.shroop.modules.product.domain.Product;

import javax.persistence.*;

@Table(name = "likes")
@Entity
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //Todo: member_id
    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    //Todo: product_id
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
