package com.kakao.borrowme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BorrowmeApplication {

    public static void main(String[] args) {
        SpringApplication.run(BorrowmeApplication.class, args);
    }

}
