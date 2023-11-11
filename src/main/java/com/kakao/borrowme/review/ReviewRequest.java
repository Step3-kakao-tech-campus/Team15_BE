package com.kakao.borrowme.review;

import lombok.Getter;
import lombok.Setter;

public class ReviewRequest {
    @Getter
    @Setter
    public static class ReviewDTO {
        private int star;
        private String content;
    }
}
