package com.kakao.borrowme.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<Product, Long> {
    // 이전 페이지의 마지막 아이템 ID를 기준으로 다음 페이지의 데이터를 조회
    @Query("SELECT p FROM Product p WHERE p.id > :lastItemId ORDER BY p.id ASC")
    Page<Product> findNextPage(@Param("lastItemId") Long lastItemId, Pageable pageable);

    List<Product> findByCategoryId(Long categoryId);
}