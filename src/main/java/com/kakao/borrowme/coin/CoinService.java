package com.kakao.borrowme.coin;

import com.kakao.borrowme._core.errors.exception.Exception400;
import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme.coin.log.CoinLogService;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.rental.Rental;
import com.kakao.borrowme.rental.RentalJPARepository;
import com.kakao.borrowme.user.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@RequiredArgsConstructor
@Service
public class CoinService {

    private final CoinJPARepository coinJPARepository;
    private final ProductJPARepository productJPARepository;
    private final CoinLogService coinLogService;
    private final RentalJPARepository rentalJPARepository;
    private final EntityManager entityManager;

    @Transactional
    public CoinResponse.GetUserCoinDTO getUserCoin(User user) {

        Coin coin = coinJPARepository.findByUserId(user.getId()).orElse(null);

        if (coin == null) {
            // 분리된 상태의 Coin 엔티티를 생성
            Coin newCoin = Coin.builder().user(user).piece(0L).build();
            // merge를 사용하여 분리된 엔티티를 영속 상태로 변경
            Coin managedCoin = entityManager.merge(newCoin);

            return new CoinResponse.GetUserCoinDTO(managedCoin);
        }

        return new CoinResponse.GetUserCoinDTO(coin);
    }

    @Transactional
    public void chargeCoin(User user, CoinRequest.ChargeCoinDTO chargeCoinDTO) {

        Coin coin = coinJPARepository.findByUserId(user.getId()).orElse(null);

        if (coin == null) {
            // 분리된 상태의 Coin 엔티티를 생성
            Coin newCoin = Coin.builder().user(user).piece(0L).build();
            // merge를 사용하여 분리된 엔티티를 영속 상태로 변경
            Coin managedCoin = entityManager.merge(newCoin);
            coin = managedCoin;
        }

        Long piece = chargeCoinDTO.getPiece();

        coin.updatePiece(coin.getPiece() + piece);

        coinJPARepository.save(coin);
        coinLogService.chargeCoinLog(coin, piece, "충전");
    }

    @Transactional
    public void useCoin(User user, Long productId, String startAt, String endAt) {

        Product product = productJPARepository.findById(productId).orElseThrow(
                () -> new Exception404("존재하지 않는 제품입니다. : " + productId,"product_not_existed"));

        Long rentalPrice = product.getRentalPrice();

        LocalDateTime startDateTime = parseDateTime(startAt);
        LocalDateTime endDateTime = parseDateTime(endAt);

        Long duration = Duration.between(startDateTime, endDateTime).toDays(); // 대여 기간 (시간)

        Long totalPrice = rentalPrice * duration;

        Coin coin = coinJPARepository.findByUserId(user.getId()).orElse(null);

        if (coin == null) {
            // 분리된 상태의 Coin 엔티티를 생성
            Coin newCoin = Coin.builder().user(user).piece(0L).build();
            // merge를 사용하여 분리된 엔티티를 영속 상태로 변경
            Coin managedCoin = entityManager.merge(newCoin);
            coin = managedCoin;
        }

        if (coin.getPiece() < totalPrice) {
            throw new Exception400("충전 페이머니가 부족합니다. 충전페이지로 이동합니다.", "create_insufficient_coin");
        }

        coin.updatePiece(coin.getPiece() - totalPrice);
        coinJPARepository.save(coin);

        Rental rental = Rental.builder().build();
        rentalJPARepository.save(rental);

        coinLogService.useCoinLog(coin, -totalPrice, "결제");
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new Exception400("잘못된 날짜 형식입니다.","wrong_date_type");
        }
    }

}
