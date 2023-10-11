package com.kakao.borrowme.coin;

import com.kakao.borrowme.user.User;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "coin_tb")
public class Coin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coin_pk")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_pk")
    private User user;

    @NotNull
    @ColumnDefault("0L")
    private Long piece = 0L;

    @Builder
    public Coin(Long id, User user, Long piece) {
        this.id = id;
        this.user = user;
        this.piece = piece;
    }

}
