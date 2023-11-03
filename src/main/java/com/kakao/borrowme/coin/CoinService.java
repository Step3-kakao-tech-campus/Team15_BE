package com.kakao.borrowme.coin;

import com.kakao.borrowme._core.errors.exception.Exception400;
import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme._core.errors.exception.InsufficientCoinException;
import com.kakao.borrowme.coin.log.CoinLogService;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.rental.Rental;
import com.kakao.borrowme.rental.RentalJPARepository;
import com.kakao.borrowme.user.User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CoinService {

    private final CoinJPARepository coinJPARepository;
    private final ProductJPARepository productJPARepository;
    private final CoinLogService coinLogService;
    private final RentalJPARepository rentalJPARepository;

    @Transactional
    public CoinResponse.GetUserCoinDTO getUserCoin(User user) {

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

        if (!coinOP.isPresent()) {
            throw new Exception404("코인 정보가 없습니다."+ user.getId(), "coin_not_existed");
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
            throw new Exception404("코인 정보가 없습니다."+ user.getId(), "coin_not_existed");
        }

        coin = coinOP.get();
        coin.updatePiece(coin.getPiece() + piece);

        coinJPARepository.save(coin);
        coinLogService.chargeCoinLog(coin, piece, "충전");

        return new CoinResponse.GetUserCoinDTO(coin);

    }

    @Transactional
    public void useCoin(User user, Long productId, String startAt, String endAt) {

        Optional<Product> productOP = productJPARepository.findById(productId);

        if (!productOP.isPresent()) {
            throw new Exception404("존재하지 않는 제품입니다. : " + productId, "product_not_existed");
        }

        Long rentalPrice = productOP.get().getRentalPrice();

        ZonedDateTime startDateTime = parseDateTime(startAt);
        ZonedDateTime endDateTime = parseDateTime(endAt);

        Long duration = Duration.between(startDateTime, endDateTime).toDays(); // 대여 기간 (시간)

        Long totalPrice = rentalPrice * duration;

        Optional<Coin> coinOP = coinJPARepository.findByUserId(user.getId());

        Coin coin = coinOP.orElseThrow(() -> new Exception404("코인 정보가 존재하지 않습니다. : " + user.getId(), "coin_not_existed"));

        if (coin.getPiece() < totalPrice) {
            throw new InsufficientCoinException("충전 페이머니가 부족합니다.");
        }

        coin.updatePiece(coin.getPiece() - totalPrice);
        coinJPARepository.save(coin);

        Rental rental = Rental.builder().status("대여중") // 대여중 상태로 설정
                .build();
        rentalJPARepository.save(rental);

        coinLogService.useCoinLog(coin, -totalPrice, "결제");
    }

    private ZonedDateTime parseDateTime(String dateTimeString) {
        try {
            return ZonedDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME);
        } catch (DateTimeParseException e) {
            throw new Exception400("잘못된 날짜 형식입니다.","wrong_date_type");
        }
    }

}
