package com.kakao.borrowme.rental;

import com.kakao.borrowme._core.errors.exception.Exception400;
import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import com.kakao.borrowme.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RentalService {

    private final RentalJPARepository rentalRepository;
    private final ProductImageJPARepository productImageRepository;

    public List<RentalResponse.getRentalDTO> getRental(User user) {
        List<Rental> rentalList = rentalRepository.findAll();

        // 대여 상태를 업데이트하는 로직 추가
        rentalList.stream()
                .filter(rental -> "예약중".equals(rental.getStatus()) && LocalDate.now().isAfter(rental.getStartAt().toLocalDate()))
                .forEach(rental -> {
                    rental.updateStatus("대여중");
                    rentalRepository.save(rental);
                });

        List<RentalResponse.getRentalDTO> responseDTOs = rentalList.stream()
                .map(rental -> {
                    Product product = rental.getProduct();
                    Company company = product.getCompany();
                    ProductImage productImage = productImageRepository.findByProductId(product.getId());
                    return new RentalResponse.getRentalDTO(product, company, rental, productImage);
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public void returnRental(Long rentalId, User user) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(
                () -> new Exception404("존재하지 않는 대여 기록입니다. : " + rentalId, "rental_not_existed")
        );

        LocalDate today = LocalDate.now();

        if (today.isBefore(rental.getEndAt().toLocalDate())) {
            throw new Exception400("반납 예정일이 아닙니다." + rental.getEndAt(), "return_early_returned");
        }

        rental.updateStatus("반납완료");
        rentalRepository.save(rental);
    }
}
