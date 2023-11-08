package com.kakao.borrowme.review;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewJPARepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProductId(Long productId);
}
