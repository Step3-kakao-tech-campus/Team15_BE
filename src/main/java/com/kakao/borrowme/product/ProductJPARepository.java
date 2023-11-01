package com.kakao.borrowme.product;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductJPARepository extends JpaRepository<Product, Long> {
    // 이전 페이지의 마지막 제품 ID를 기준으로 다음 페이지의 데이터를 조회
    @Query("SELECT p FROM Product p WHERE p.id > :lastProductId ORDER BY p.id ASC")
    Slice<Product> findNextPage(@Param("lastProductId") Long lastProductId, Pageable pageable);
  
    @Query("SELECT p FROM Product p WHERE p.id > :lastProductId AND p.name LIKE %:keyword% ORDER BY p.id ASC")
    Slice<Product> searchProduct(@Param("lastProductId") Long lastProductId, @Param("keyword") String keyword, Pageable pageable);

    List<Product> findByCategoryId(Long categoryId);
}
