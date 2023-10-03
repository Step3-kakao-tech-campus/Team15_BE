package com.kakao.borrowme.coin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinJPARepository extends JpaRepository<Coin, Long> {
}
