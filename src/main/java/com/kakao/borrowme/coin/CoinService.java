package com.kakao.borrowme.coin;

import com.kakao.borrowme.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoinService {

    private final CoinJPARepository coinJPARepository;

    @Autowired
    public CoinService(CoinJPARepository coinJPARepository) {
        this.coinJPARepository = coinJPARepository;
    }

    public CoinResponse.FindByIdDTO getUserCoin(User user) {

        Optional<Coin> coinOptional = coinJPARepository.findByUserId(user.getId());
        Coin coin;

        if (coinOptional.isPresent()) {
            coin = coinOptional.get();
        } else {
            // 코인 정보가 없을 경우 기본값으로 0으로 설정
            coin = Coin.builder()
                    .user(user)
                    .piece(0L)
                    .build();
        }

        return new CoinResponse.FindByIdDTO(coin.getPiece());
    }


}