package com.kakao.borrowme.coin.log;

import com.kakao.borrowme.coin.Coin;
import com.kakao.borrowme.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CoinLogService {

    private final CoinLogJPARepository coinLogJPARepository;

    public void chargeCoinLog(Coin coin, Long piece, String coinType) {

        CoinLog coinLog = CoinLog.builder()
                .coin(coin)
                .piece(piece)
                .coinType(coinType)
                .createAt(LocalDateTime.now())
                .build();

        coinLogJPARepository.save(coinLog);

    }

    public void useCoinLog(Coin coin, Long piece, String coinType) {

        CoinLog coinLog = CoinLog.builder()
                .coin(coin)
                .piece(piece)
                .coinType(coinType)
                .createAt(LocalDateTime.now())
                .build();

        coinLogJPARepository.save(coinLog);

    }

    public CoinLogResponse.GetUserCoinLogDTO getUserCoinLog(User user) {

        List<CoinLog> coinLogs = coinLogJPARepository.findByUserId(user.getId());

        return new CoinLogResponse.GetUserCoinLogDTO(coinLogs);

    }
}
