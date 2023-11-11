package com.kakao.borrowme.coin;

import lombok.Getter;
import lombok.Setter;

public class CoinResponse {
    @Getter
    @Setter
    public static class GetUserCoinDTO {
        private Long piece;

        public GetUserCoinDTO(Coin coin) {
            this.piece = coin.getPiece();
        }
    }
}
