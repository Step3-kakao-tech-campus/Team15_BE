package com.kakao.borrowme.review;

import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.rental.Rental;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "review_tb")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_pk")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_pk")
    private Rental rental;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_pk")
    private Product product;

    public void updateProduct(@NotNull Product product) {
        this.product = product;
    }

    @NotNull
    private int star;

    public void updateStar(@NotNull int star) {
        this.star = star;
    }

    @NotNull
    @Column(length = 600)
    private String content;

    public void updateContent(@NotNull String content) {
        this.content = content;
    }

    @NotNull
    @CreatedDate
    private LocalDateTime createAt;

    @NotNull
    @LastModifiedDate
    private LocalDateTime updateAt;

    private LocalDateTime deleteAt;

    @Builder
    public Review(Long id, Rental rental, Product product, int star, String content, LocalDateTime createAt, LocalDateTime updateAt, LocalDateTime deleteAt) {
        this.id = id;
        this.rental = rental;
        this.product = product;
        this.star = star;
        this.content = content;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }
}
