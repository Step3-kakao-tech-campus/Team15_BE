package com.kakao.borrowme.coin.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CoinLogJPARepository extends JpaRepository<CoinLog, Long> {
    @Query("SELECT cl FROM CoinLog cl JOIN FETCH cl.coin c JOIN FETCH c.user u WHERE u.id = :userId")
    List<CoinLog> findByUserId(@Param("userId") Long id);
}
