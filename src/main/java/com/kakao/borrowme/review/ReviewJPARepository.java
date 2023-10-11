package com.kakao.borrowme.review;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewJPARepository extends JpaRepository<Review, Long> {
}
