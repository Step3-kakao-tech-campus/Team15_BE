package com.kakao.borrowme.category;

import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.image.ProductImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class CategoryResponse {

    @Getter @Setter
    public static class CategoryDTO {
        private List<Category> categories;

        public CategoryDTO(List<Category> categories) {
            this.categories = categories;
        }
    }

    @Getter @Setter
    public static class ProductDTO {

        private Long productId;
        private String productName;
        private Long rentalPrice;
        private Long regularPrice;
        private String productImagePath;

        public ProductDTO(Product product, ProductImage productImage) {
            this.productId = product.getId();
            this.productName = product.getName();
            this.rentalPrice = product.getRentalPrice();
            this.regularPrice = product.getRegularPrice();
            this.productImagePath = productImage.getProductImagePath();
        }
    }
}
