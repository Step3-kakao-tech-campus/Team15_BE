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
import java.time.LocalDate;
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
            Coin newCoin = Coin.builder().user(user).piece(0L).build();
            Coin managedCoin = entityManager.merge(newCoin);
            coin = managedCoin;
        }

        Long piece = chargeCoinDTO.getPiece();

        coin.updatePiece(coin.getPiece() + piece);

        coinJPARepository.save(coin);
        coinLogService.coinLog(coin, piece, "충전"); // 코인 충전 내역 추가
    }

    @Transactional
    public void useCoin(User user, Long productId, String startAt, String endAt) {

        Product product = productJPARepository.findById(productId).orElseThrow(
                () -> new Exception404("존재하지 않는 제품입니다. : " + productId,"product_not_existed"));

        Long rentalPrice = product.getRentalPrice();

        LocalDateTime startDateTime = parseDateTime(startAt);
        LocalDateTime endDateTime = parseDateTime(endAt);

        // 예약 종료일이 예약 시작일보다 빠른 경우 예외 처리
        if (startDateTime.toLocalDate().isAfter(endDateTime.toLocalDate())) {
            throw new Exception400("예약 종료일이 예약 시작일보다 빠릅니다. : " + endAt, "rent_incorrect_period");
        }

        // 선택된 날짜가 과거로 설정 경우 예외 처리
        LocalDate today = LocalDate.now();
        if (startDateTime.toLocalDate().isBefore(today) || endDateTime.toLocalDate().isBefore(today)) {
            throw new Exception400("과거 날짜값은 대여할 수 없습니다. : " + endAt, "rent_past_period");
        }

        // 대여 기간 계산
        Long duration = Duration.between(startDateTime, endDateTime).toDays() + 1;
        Long totalPrice = rentalPrice * duration;

        Coin coin = coinJPARepository.findByUserId(user.getId()).orElse(null);

        if (coin == null) {
            Coin newCoin = Coin.builder().user(user).piece(0L).build();
            Coin managedCoin = entityManager.merge(newCoin);
            coin = managedCoin;
        }

        // 충전 잔액이 부족한 경우 예외처리
        if (coin.getPiece() < totalPrice) {
            throw new Exception400("충전 페이머니가 부족합니다. 충전페이지로 이동합니다. : " + coin.getPiece(), "create_insufficient_coin");
        }

        coin.updatePiece(coin.getPiece() - totalPrice); // 결제 완료
        coinJPARepository.save(coin);

        String rentalStatus = (startDateTime.toLocalDate().isEqual(today)) ? "대여중" : "예약중";

        Rental rental = Rental.builder().product(product).user(user).status(rentalStatus)
                                        .startAt(startDateTime).endAt(endDateTime).build();
        rentalJPARepository.save(rental);

        coinLogService.coinLog(coin, -totalPrice, "결제");
    }

    private LocalDateTime parseDateTime(String dateTimeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new Exception400("잘못된 날짜 형식입니다.","wrong_date_type");
        }
    }

}
