package com.kakao.borrowme.review;

import lombok.Getter;
import lombok.Setter;

public class ReviewResponse {
    @Getter
    @Setter
    public static class ReviewDTO {
        private int star;
        private String content;

        public ReviewDTO(Review review) {
            this.star = review.getStar();
            this.content = review.getContent();
        }
    }
}
