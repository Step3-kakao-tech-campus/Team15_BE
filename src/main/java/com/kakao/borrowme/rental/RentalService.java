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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RentalService {

    private final RentalJPARepository rentalRepository;
    private final ProductImageJPARepository productImageRepository;

    public List<RentalResponse.getRentalDTO> getRental() {
        List<Rental> rentalList = rentalRepository.findAll();

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

        LocalDateTime currentDateTime = LocalDateTime.now();

        if (currentDateTime.isBefore(rental.getEndAt())) {
            throw new Exception400("반납 예정일이 아닙니다." + rental.getEndAt(), "return_early_returned");
        }

        rental.setStatus("반납완료");
        rentalRepository.save(rental);
    }
}
