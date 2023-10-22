package com.kakao.borrowme._core.utils;

import com.kakao.borrowme.category.Category;
import com.kakao.borrowme.category.CategoryJPARepository;
import com.kakao.borrowme.company.Company;
import com.kakao.borrowme.location.Location;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.product.image.ProductImage;
import com.kakao.borrowme.product.image.ProductImageJPARepository;
import com.kakao.borrowme.university.University;
import com.kakao.borrowme.coin.Coin;
import com.kakao.borrowme.coin.CoinJPARepository;
import com.kakao.borrowme.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Component
public class DBInit {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDB(CoinJPARepository coinJPARepository, CategoryJPARepository categoryJPARepository,
                             ProductJPARepository productJPARepository, ProductImageJPARepository productImageJPARepository) {
        return args -> {
            Coin coin = Coin.builder()
                    .user(
                            User.builder()
                                    .university(
                                            University.builder()
                                                    .name("testUniv")
                                                    .build())
                                    .email("test@naver.com")
                                    .password(passwordEncoder.encode("1234"))
                                    .nickname("testNick")
                                    .role("student")
                                    .build()
                    )
                    .piece(200L)
                    .build();
            coinJPARepository.save(coin);

            ProductImage productImage = ProductImage.builder()
                    .product(Product.builder()
                            .name("Apple 2023 맥북프로 14 M2 Pro")
                            .location(Location.builder()
                                    .university(University.builder()
                                            .name("testUniv")
                                            .build())
                                    .latitude(new BigDecimal("1.0"))
                                    .longitude(new BigDecimal("1.0"))
                                    .name("제 1 학생회관")
                                    .build())
                            .company(Company.builder()
                                    .name("Apple")
                                    .build())
                            .category(Category.builder()
                                    .name("Electronics")
                                    .build())
                            .rentalPrice(5000L) // 대여 가격
                            .regularPrice(2790000L) // 정가
                            .content("테스트 상품 설명")
                            .createAt(LocalDateTime.now()) // 현재 날짜와 시간
                            .build()).productImagePath("path/to/img.png").build();
            productImageJPARepository.save(productImage);


//            Product product1 = Product.builder()
//                    .name("Apple 2023 맥북프로 14 M2 Pro")
//                    .location(Location.builder()
//                            .university(University.builder()
//                                    .name("testUniv")
//                                    .build())
//                            .latitude(new BigDecimal("1.0"))
//                            .longitude(new BigDecimal("1.0"))
//                            .name("제 1 학생회관")
//                            .build())
//                    .company(Company.builder()
//                            .name("Apple")
//                            .build())
//                    .category(Category.builder()
//                            .name("Electronics")
//                            .build())
//                    .rentalPrice(5000L) // 대여 가격
//                    .regularPrice(2790000L) // 정가
//                    .content("테스트 상품 설명")
//                    .createAt(LocalDateTime.now()) // 현재 날짜와 시간
//                    .build();
//
//            productJPARepository.save(product1);
//
//            Product product2 = Product.builder()
//                    .name("개념이 보이는 물리전자공학")
//                    .location(Location.builder()
//                            .university(University.builder()
//                                    .name("testUniv")
//                                    .build())
//                            .latitude(new BigDecimal("1.0"))
//                            .longitude(new BigDecimal("1.0"))
//                            .name("제 2 학생회관")
//                            .build())
//                    .company(Company.builder()
//                            .name("한빛아카데미")
//                            .build())
//                    .category(Category.builder()
//                            .name("Book")
//                            .build())
//                    .rentalPrice(1000L) // 대여 가격
//                    .regularPrice(24000L) // 정가
//                    .content("테스트 상품 설명")
//                    .createAt(LocalDateTime.now()) // 현재 날짜와 시간
//                    .build();
//
//            productJPARepository.save(product2);

        };
    }
}
