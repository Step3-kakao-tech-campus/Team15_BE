package com.kakao.borrowme.product;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

@EntityListeners(AuditingEntityListener.class)
public class Product {
}
