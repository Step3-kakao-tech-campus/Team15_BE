package com.kakao.borrowme.coin;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

public class CoinRequest {

    @Getter @Setter @ToString
    public static class ChargeCoinDTO {

        private Long piece;

    }

    @Getter @Setter @ToString
    public static class UseCoinDTO {

        private String startAt;
        private String endAt;

    }

}
