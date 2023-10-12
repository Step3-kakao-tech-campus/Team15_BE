package com.kakao.borrowme.coin;

import lombok.Getter;
import lombok.Setter;

public class CoinResponse {
    
    @Getter @Setter
    public static class CoinInfoDTO {

        private Long piece;

        public CoinInfoDTO(Coin coin) {
            this.piece = coin.getPiece();
        }

    }

}