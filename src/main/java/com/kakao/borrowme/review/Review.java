package com.kakao.borrowme.review;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;

@EntityListeners(AuditingEntityListener.class)
public class Review {
}
