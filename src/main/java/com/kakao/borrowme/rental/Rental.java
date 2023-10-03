package com.kakao.borrowme.rental;

import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "rental_tb")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_pk")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_pk")
    private User user;

    @NotNull
    private LocalDateTime startAt;

    @NotNull
    private LocalDateTime endAt;

    @NotNull
    @Column(length = 45)
    private String status;

    @Builder
    public Rental(Long id, Product product, User user, LocalDateTime startAt, LocalDateTime endAt, String status) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.startAt = startAt;
        this.endAt = endAt;
        this.status = status;
    }
}
