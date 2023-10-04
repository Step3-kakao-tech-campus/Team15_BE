package com.kakao.borrowme.coin;

import lombok.Getter;

@Getter
public class CoinDTO {
    private Long id;
    private Long userId;
    private Long piece;

    public CoinDTO(Coin coin) {
        this.id = coin.getId();
        this.userId = coin.getUser().getId();
        this.piece = coin.getPiece();
    }
}