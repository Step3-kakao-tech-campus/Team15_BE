package com.kakao.borrowme.coin;

import lombok.Getter;

@Getter
public class CoinResponse {
    
    public CoinResponse() { }

    public static class FindByIdDTO {

        private Long piece;

        public FindByIdDTO(Long piece) {
            this.piece = piece;
        }

    }

    public static class CoinDTO {

        private Long totalPrice;

        public CoinDTO(Long totalPrice) {
            this.totalPrice = totalPrice;
        }
    }

}