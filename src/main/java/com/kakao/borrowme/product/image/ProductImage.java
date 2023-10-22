package com.kakao.borrowme.product.image;

import com.kakao.borrowme.product.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "product_image_tb")
public class ProductImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_image_pk")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "product_pk")
    private Product product;

    @Column(length = 200)
    private String productImagePath;

    @Builder
    public ProductImage(long id, Product product, String productImagePath) {
        this.id = id;
        this.product = product;
        this.productImagePath = productImagePath;
    }
}
