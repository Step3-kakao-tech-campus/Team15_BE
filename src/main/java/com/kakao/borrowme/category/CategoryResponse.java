package com.kakao.borrowme.category;

import com.kakao.borrowme.location.Location;
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
        private String categoryName;
        private String productName;
        private Long rentalPrice;
        private Long regularPrice;
        private Location location;
        private String productImagePath;

        public ProductDTO(Product product, ProductImage productImage) {
            this.productId = product.getId();
            this.categoryName = product.getCategory().getName();
            this.productName = product.getName();
            this.rentalPrice = product.getRentalPrice();
            this.regularPrice = product.getRegularPrice();
            this.location = product.getLocation();
            this.productImagePath = productImage.getProductImagePath();
        }
    }
}
