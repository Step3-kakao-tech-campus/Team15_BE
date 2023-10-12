package com.kakao.borrowme.coin;

import lombok.Getter;
import lombok.Setter;

public class CoinResponse {
    
    @Getter @Setter
    public static class getUserCoinDTO {

        private Long piece;

        public getUserCoinDTO(Coin coin) {
            this.piece = coin.getPiece();
        }

    }

}