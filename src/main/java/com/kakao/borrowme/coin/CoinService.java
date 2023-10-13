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
    public CoinResponse.GetUserCoinDTO getUserCoin(User user) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

        if (!coinOP.isPresent()) {
            log.error("코인 정보가 없습니다.");
            return null; // exception 수정 예정
        }

        Coin coin = coinOP.get();
        return new CoinResponse.GetUserCoinDTO(coin);

    }

    @Transactional
    public CoinResponse.GetUserCoinDTO chargeCoin(User user, CoinRequest.ChargeCoinDTO chargeCoinDTO) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());
        Coin coin;
        Long piece = chargeCoinDTO.getPiece();

        if (!coinOP.isPresent()) {
            log.error("코인 정보가 없습니다.");
            return null; // exception 수정 예정
        }

        coin = coinOP.get();
        coin.updatePiece(coin.getPiece() + piece);

        coinJPARepository.save(coin);
        return new CoinResponse.GetUserCoinDTO(coin);

    }

    @Transactional
    public void useCoin(User user, Long productId, LocalDateTime startAt, LocalDateTime endAt) {

        Optional<Product> productOP = productJPARepository.findById(productId);

        if (!productOP.isPresent()) {

            // 상품이 존재하지 않는 경우에 대한 예외 처리 필요
            log.error("상품이 존재하지 않습니다.");

        } else {

            Long rentalPrice = productOP.get().getRentalPrice();

            Long durationInHours = Duration.between(startAt, endAt).toHours(); // 대여 기간 (시간)
            Long durationInDays = durationInHours / 24; // 대여 기간 (일)

            Long totalPrice = rentalPrice * durationInDays;

            Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

            Coin coin = coinOP.get();

            if (coin.getPiece() < totalPrice) {

                // 코인 잔액이 부족한 경우에 대한 에러처리
                // throw new create_insufficient_coinCo
                log.error("코인이 부족합니다.");

            } else {

                coin.updatePiece(coin.getPiece() - totalPrice);
                coinJPARepository.save(coin);

            }

        }

    }

}
