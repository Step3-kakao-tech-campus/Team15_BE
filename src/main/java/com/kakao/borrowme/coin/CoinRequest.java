package com.kakao.borrowme.coin;

import lombok.Getter;
import lombok.Setter;

public class CoinRequest {
    @Getter
    @Setter
    public static class ChargeCoinDTO {
        private Long piece;
    }

    @Getter
    @Setter
    public static class UseCoinDTO {
        private String startAt;
        private String endAt;
    }
}
