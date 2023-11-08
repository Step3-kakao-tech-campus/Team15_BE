package com.kakao.borrowme.product;

import com.kakao.borrowme._core.errors.exception.Exception400;
import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.company.CompanyJPARepository;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductJPARepository productRepository;
    private final ProductImageJPARepository productImageRepository;
    private final CompanyJPARepository companyRepository;

    public Slice<ProductResponse.FindAllDTO> findAll(Long lastProductId, Pageable pageable) {
        Slice<Product> productSlice = productRepository.findNextPage(lastProductId, pageable);
        Slice<ProductResponse.FindAllDTO> responseDTOSlice = productSlice.map(product -> {
            ProductImage productImage = productImageRepository.findByProductId(product.getId());
            return new ProductResponse.FindAllDTO(product, productImage);
        });
        return responseDTOSlice;
    }

    public ProductResponse.FindByIdDTO findById(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new Exception404("존재하지 않는 제품입니다. : "+productId,"product_not_existed"));

        ProductImage productImage = productImageRepository.findByProductId(productId); // ProductImage 정보 조회

        Company company = companyRepository.findById(product.getCompany().getId()).orElseThrow(
                () -> new Exception404("존재하지 않는 회사입니다. : "+productId,"company_not_existed")); // Company 정보 조회
        return new ProductResponse.FindByIdDTO(product, productImage, company);
    }

    public Slice<ProductResponse.FindAllDTO> searchProduct(Long lastProductId, String keyword, Pageable pageable) {
        Slice<Product> productSlice = productRepository.searchProduct(lastProductId, keyword, pageable);
        Slice<ProductResponse.FindAllDTO> responseDTOSlice = productSlice.map(product -> {
            ProductImage productImage = productImageRepository.findByProductId(product.getId());
            return new ProductResponse.FindAllDTO(product, productImage);
        });
        return responseDTOSlice;
    }

    public ProductResponse.RentDTO rentProduct(Long productId, ProductRequest.RentDTO requestDTO) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new Exception404("존재하지 않는 제품입니다. : " + productId, "product_not_existed"));

        LocalDateTime startAt = requestDTO.getStartAt();
        LocalDateTime endAt = requestDTO.getEndAt();

        // 예약 종료일이 예약 시작일보다 빠른 경우 예외 처리
        if (startAt.isAfter(endAt)) {
            throw new Exception400("예약 종료일이 예약 시작일보다 빠릅니다. : " + endAt, "rent_incorrect_period");
        }

        // 선택된 날짜가 과거로 설정 경우 예외 처리
        LocalDateTime now = LocalDateTime.now();
        if (startAt.isBefore(now) || endAt.isBefore(now)) {
            throw new Exception400("과거 날짜값은 대여할 수 없습니다. : " + endAt, "rent_past_period");
        }

        Long rentalPrice = product.getRentalPrice();
        Long totalPrice = calculateTotalPrice(startAt, endAt, rentalPrice);

        ProductImage productImage = productImageRepository.findByProductId(productId);

        Company company = companyRepository.findById(product.getCompany().getId()).orElseThrow(
                () -> new Exception404("존재하지 않는 회사입니다. : " + productId, "company_not_existed"));

        return new ProductResponse.RentDTO(product, totalPrice, productImage, company);
    }

    // 대여 비용 계산
    private Long calculateTotalPrice(LocalDateTime startAt, LocalDateTime endAt, Long rentalPrice) {
        Long duration = Duration.between(startAt, endAt).toDays();
        return rentalPrice * duration;
    }
}
