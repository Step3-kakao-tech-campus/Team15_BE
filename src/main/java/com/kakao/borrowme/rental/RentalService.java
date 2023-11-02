package com.kakao.borrowme.rental;

import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class RentalService {

    private final RentalJPARepository rentalRepository;
    private final ProductImageJPARepository productImageRepository;

    public List<RentalResponse.FindAllDTO> findAll() {
        List<Rental> rentalRecords = rentalRepository.findAll();

        List<RentalResponse.FindAllDTO> responseDTOs = rentalRecords.stream()
                .map(rental -> {
                    Product product = rental.getProduct();
                    Company company = product.getCompany();
                    ProductImage productImage = productImageRepository.findByProductId(product.getId());
                    return new RentalResponse.FindAllDTO(product, company, rental, productImage);
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }
}
