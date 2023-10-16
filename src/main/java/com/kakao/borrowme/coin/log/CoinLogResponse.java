package com.kakao.borrowme.coin.log;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

public class CoinLogResponse {

    @Getter @Setter
    public static class GetUserCoinLogDTO {

        private List<CoinLogDTO> coinLogs;


        public GetUserCoinLogDTO(List<CoinLog> coinLogList) {
            this.coinLogs = coinLogList.stream().map(CoinLogDTO::new).collect(Collectors.toList());
        }

        public class CoinLogDTO{
            private Long id;
            private Long piece;
            private String coinType;
            private String createAt;

            public CoinLogDTO(CoinLog coinLog) {
                this.id = coinLog.getId();
                this.piece = coinLog.getPiece();
                this.coinType = coinLog.getCoinType();
                this.createAt = coinLog.getCreateAt().toString();
            }
        }
    }
}
