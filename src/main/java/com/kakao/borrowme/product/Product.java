package com.kakao.borrowme.product;

import com.kakao.borrowme.category.Category;
import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.location.Location;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "product_tb")
public class Product {
    // 추후 String 으로 변경 가능
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_pk")
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_pk")
    private Company company;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_pk")
    private Category category;

    @NotNull
    @Column(length = 45)
    private String name;

    @NotNull
    private Long rentalPrice;

    @NotNull
    private Long regularPrice;

    @NotNull
    private String content;

    @NotNull
    @CreatedDate
    private LocalDateTime createAt;

    private LocalDateTime deleteAt;

    @Builder
    public Product(Long id, Location location, Company company, Category category, String name, Long rentalPrice, Long regularPrice, String content, LocalDateTime createAt, LocalDateTime deleteAt) {
        this.id = id;
        this.location = location;
        this.company = company;
        this.category = category;
        this.name = name;
        this.rentalPrice = rentalPrice;
        this.regularPrice = regularPrice;
        this.content = content;
        this.createAt = createAt;
        this.deleteAt = deleteAt;
    }
}
