package com.kakao.borrowme.coin.log;

import com.kakao.borrowme.coin.Coin;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coin_log_tb")
public class CoinLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_log_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coin_pk")
    private Coin coin;

    @NotNull
    private Long piece;

    @NotNull
    @Column(length = 45)
    private String coinType;

    @NotNull
    @CreatedDate
    private LocalDateTime createAt;

    @Builder
    public CoinLog(Long id, Coin coin, Long piece, String coinType, LocalDateTime createAt) {
        this.id = id;
        this.coin = coin;
        this.piece = piece;
        this.coinType = coinType;
        this.createAt = createAt;
    }
}
