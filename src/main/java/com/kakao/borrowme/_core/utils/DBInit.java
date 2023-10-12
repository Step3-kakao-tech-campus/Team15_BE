package com.kakao.borrowme._core.utils;

import com.kakao.borrowme.university.University;
import com.kakao.borrowme.coin.Coin;
import com.kakao.borrowme.coin.CoinJPARepository;
import com.kakao.borrowme.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DBInit {
    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDB(CoinJPARepository coinJPARepository) {
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
        };
    }
}
