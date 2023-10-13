package com.kakao.borrowme.coin;

import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.user.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CoinService {

    private final CoinJPARepository coinJPARepository;
    private final ProductJPARepository productJPARepository;

    @Transactional
    public CoinResponse.CoinInfoDTO getUserCoin(User user) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

        // TODO : 코인 정보가 없는 경우 예외처리
        if (!coinOP.isPresent()) {
            log.error("코인 정보가 없습니다.");
            return null;
        }

        Coin coin = coinOP.get();
        return new CoinResponse.CoinInfoDTO(coin);

    }

    @Transactional
    public CoinResponse.CoinInfoDTO chargeCoin(User user, CoinRequest.ChargeCoinDTO chargeCoinDTO) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());
        Coin coin;
        Long piece = chargeCoinDTO.getPiece();

        // TODO : 코인 정보가 없는 경우 예외처리
        if (!coinOP.isPresent()) {
            log.error("코인 정보가 없습니다.");
            return null;
        }

        coin = coinOP.get();
        coin.setPiece(coin.getPiece() + piece);

        coinJPARepository.save(coin);
        return new CoinResponse.CoinInfoDTO(coin);

    }

    @Transactional
    public void useCoin(User user, Long productId, LocalDateTime startAt, LocalDateTime endAt) {

        Optional<Product> productOP = productJPARepository.findById(productId);

        if (!productOP.isPresent()) {

            // TODO : 상품이 존재하지 않는 경우에 대한 예외 처리 필요
            log.error("상품이 존재하지 않습니다.");
            return;

        }

        Long rentalPrice = productOP.get().getRentalPrice();

        Long durationInHours = Duration.between(startAt, endAt).toHours(); // 대여 기간 (시간)
        Long durationInDays = durationInHours / 24; // 대여 기간 (일)

        Long totalPrice = rentalPrice * durationInDays;

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

        Coin coin = coinOP.get();

        if (coin.getPiece() < totalPrice) {

            // TODO : 코인 잔액이 부족한 경우에 대한 에러처리
            // throw new create_insufficient_coinCo
            log.error("코인이 부족합니다.");
            return;

        }

        coin.setPiece(coin.getPiece() - totalPrice);
        coinJPARepository.save(coin);

    }

}
