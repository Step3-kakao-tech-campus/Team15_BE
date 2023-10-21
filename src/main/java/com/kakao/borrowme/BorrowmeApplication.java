package com.kakao.borrowme;

import com.kakao.borrowme._core.security.CustomUserDetails;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableJpaAuditing
@SpringBootApplication
public class BorrowmeApplication {
    @GetMapping("/test")
    public String test(@AuthenticationPrincipal CustomUserDetails userDetails) {
        System.out.println(userDetails.getUser().getNickname());
        return userDetails.getUsername();
    }

    public static void main(String[] args) {
        SpringApplication.run(BorrowmeApplication.class, args);
    }
}
