package com.kakao.borrowme.coin;

import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.user.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class CoinService {

    private final CoinJPARepository coinJPARepository;
    private final ProductJPARepository productJPARepository;

    public CoinResponse.FindByIdDTO getUserCoin(User user) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

        if (!coinOP.isPresent()) {
            log.error("코인 정보가 없습니다.");
            return null; // exception 수정 예정
        }

        Coin coin = coinOP.get();
        return new CoinResponse.FindByIdDTO(coin);

    }

    public CoinResponse.FindByIdDTO chargeCoin(User user, CoinRequest.ChargeCoinDTO chargeCoinDTO) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());
        Coin coin;
        Long piece = chargeCoinDTO.getPiece();

        if (!coinOP.isPresent()) {
            log.error("코인 정보가 없습니다.");
            return null; // exception 수정 예정
        }

        coin = coinOP.get();
        coin.setPiece(coin.getPiece() + piece);

        coinJPARepository.save(coin);
        return new CoinResponse.FindByIdDTO(coin);

    }

    public void useCoin(User user, String productId, LocalDateTime startAt, LocalDateTime endAt) {

        Optional<Product> productOP = ProductJPARepository.findById(productId);

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

                // 코인 잔액이 부족한 경우에 대한 예외 처리
                log.error("코인이 부족합니다.");

            } else {

                coin.setPiece(coin.getPiece() - totalPrice);
                coinJPARepository.save(coin);

            }

        }

    }

}
