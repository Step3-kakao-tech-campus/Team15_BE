package com.kakao.borrowme.rental;

import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.image.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class RentalResponse {

    @Getter
    @Setter
    public static class getRentalDTO {

        private Long id;
        private String productName;
        private String companyName;
        private LocalDateTime startAt;
        private LocalDateTime endAt;
        private String productImagePath;
        private String status;

        public getRentalDTO(Product product, Company company, Rental rental, ProductImage productImage) {
            this.id = product.getId();
            this.productName = product.getName();
            this.companyName = company.getName();
            this.startAt = rental.getStartAt();
            this.endAt = rental.getEndAt();
            this.productImagePath = productImage.getProductImagePath();
            this.status = rental.getStatus();
        }
    }
}
