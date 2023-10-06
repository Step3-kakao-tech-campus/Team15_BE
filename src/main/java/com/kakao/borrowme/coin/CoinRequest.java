package com.kakao.borrowme.coin;

public class CoinRequest {

    public CoinRequest() {}

    public static class ChargeCoinDTO {
        private Long piece;
        public Long getPiece() {
            return piece;
        }

    }

    public static class UseCoinDTO {
        private Long totalPrice;
        public Long getTotalPrice() {return totalPrice;}
    }
}

