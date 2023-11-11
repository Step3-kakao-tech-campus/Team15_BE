package com.kakao.borrowme.product;

import lombok.Getter;
import lombok.Setter;

public class ProductRequest {
    @Getter
    @Setter
    public static class RentDTO {
        private String startAt;
        private String endAt;
    }
}
