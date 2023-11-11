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
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
public class RentalService {
    private final RentalJPARepository rentalJPARepository;
    private final ProductImageJPARepository productImageJPARepository;

    public List<RentalResponse.RentalDTO> getRental(User user) {
        List<Rental> rentalList = rentalJPARepository.findAll();

        // 대여 상태를 업데이트하는 로직 추가
        rentalList.stream()
                .filter(rental -> "예약중".equals(rental.getStatus()) &&
                       (LocalDate.now().isAfter(rental.getStartAt().toLocalDate()) || LocalDate.now().isEqual(rental.getStartAt().toLocalDate())))
                .forEach(rental -> {
                    rental.updateStatus("대여중");
                    rentalJPARepository.save(rental);
                });

        List<RentalResponse.RentalDTO> responseDTOs = rentalList.stream()
                .map(rental -> {
                    Product product = rental.getProduct();
                    Company company = product.getCompany();
                    ProductImage productImage = productImageJPARepository.findByProductId(product.getId());
                    return new RentalResponse.RentalDTO(product, company, rental, productImage);
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public void returnRental(Long rentalId, User user) {
        Rental rental = rentalJPARepository.findById(rentalId).orElseThrow(
                () -> new Exception404("존재하지 않는 대여 기록입니다.:" + rentalId, "rental_not_existed")
        );

        LocalDate today = LocalDate.now();

        if (today.isBefore(rental.getEndAt().toLocalDate())) {
            throw new Exception400("반납 예정일이 아닙니다.:" + rental.getEndAt(), "return_early_returned");
        }

        rental.updateStatus("반납완료");
        rentalJPARepository.save(rental);
    }
}
