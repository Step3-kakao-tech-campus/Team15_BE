package com.kakao.borrowme.coin;

import com.kakao.borrowme.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoinJPARepository extends JpaRepository<Coin, Long> {

    Optional<Coin> findByUserId(Long userId);


}
