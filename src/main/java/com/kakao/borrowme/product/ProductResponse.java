package com.kakao.borrowme.product;

import com.kakao.borrowme.category.Category;
import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.location.Location;
import com.kakao.borrowme.product.image.ProductImage;
import lombok.Getter;
import lombok.Setter;

public class ProductResponse {

    @Getter
    @Setter
    public static class FindAllDTO {

        private Long id;
        private Category category;
        private Long rentalPrice;
        private Long regularPrice;
        private Location location;
        private String productName;
        private String productImagePath;


        public FindAllDTO(Product product,ProductImage productImage) {
            this.id = product.getId();
            this.category = product.getCategory();
            this.rentalPrice = product.getRentalPrice();
            this.regularPrice = product.getRegularPrice();
            this.location = product.getLocation();
            this.productName = product.getName();
            this.productImagePath = productImage.getProductImagePath();
        }
    }

    @Getter
    @Setter
    public static class FindByIdDTO {

        private Long id;
        private Category category;
        private Long rentalPrice;
        private Long regularPrice;
        private String content;
        private Location location;
        private String productName;
        private String productImagePath;
        private String companyName;
        private String companyImagePath;

        public FindByIdDTO(Product product, ProductImage productImage, Company company) {
            this.id = product.getId();
            this.category = product.getCategory();
            this.rentalPrice = product.getRentalPrice();
            this.regularPrice = product.getRegularPrice();
            this.location = product.getLocation();
            this.productName = product.getName();
            this.productImagePath = productImage.getProductImagePath();
            this.companyName = company.getName();
            this.companyImagePath = company.getCompanyImagePath();
        }
    }
}
