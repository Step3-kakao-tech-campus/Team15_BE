package com.kakao.borrowme.coin;

import lombok.Getter;
import lombok.Setter;

public class CoinResponse {
    
    @Getter @Setter
    public static class FindByIdDTO {

        private Long piece;

        public FindByIdDTO(Coin coin) {
            this.piece = coin.getPiece();
        }

    }

}