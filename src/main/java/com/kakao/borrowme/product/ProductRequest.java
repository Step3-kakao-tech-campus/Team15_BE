package com.kakao.borrowme.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

public class ProductRequest {

    @Getter
    @Setter
    @ToString
    public static class RentDTO {

        private LocalDateTime startAt;
        private LocalDateTime endAt;

    }
}
