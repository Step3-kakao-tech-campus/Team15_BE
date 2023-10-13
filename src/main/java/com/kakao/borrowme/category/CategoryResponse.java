package com.kakao.borrowme.category;

import lombok.Getter;
import lombok.Setter;

public class CategoryResponse {

    @Getter @Setter
    public static class CategoryDTO {

        private Long id;
        private String name;


        public CategoryDTO(Long id, String name) {
            this.id = id;
            this.name = name;
        }

    }

    @Getter @Setter
    public static class ProductDTO {

        private Long productId;
        private String productName;
        private Long rentalPrice;
        private Long regularPrice;
        private String productImagePath;

        public ProductDTO(Long productId, String productName, Long rentalPrice, Long regularPrice, String productImagePath) {
            this.productId = productId;
            this.productName = productName;
            this.rentalPrice = rentalPrice;
            this.regularPrice = regularPrice;
            this.productImagePath = productImagePath;
        }
    }
}
