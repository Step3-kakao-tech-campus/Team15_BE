package com.kakao.borrowme.user;

import com.kakao.borrowme.university.University;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "user_tb")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "university_pk")
    private University university;

    @NotNull
    @Column(length = 45)
    private String email;

    // 해시 알고리즘 저장 필요
    @NotNull
    private String password;

    @NotNull
    @Column(length = 45)
    private String nickname;

    @Column(length = 45)
    private String role;

    @Column(length = 200)
    private String idCardImagePath;

    @Column(length = 200)
    private String profileImagePath;

    @NotNull
    @CreatedDate
    private LocalDateTime createAt;

    @NotNull
    @LastModifiedDate
    private LocalDateTime updateAt;

    private LocalDateTime deleteAt;

    @Builder
    public User(Long id, University university, String email, String password, String nickname, String role, String idCardImagePath, String profileImagePath, LocalDateTime createAt, LocalDateTime updateAt, LocalDateTime deleteAt) {
        this.id = id;
        this.university = university;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
        this.idCardImagePath = idCardImagePath;
        this.profileImagePath = profileImagePath;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.deleteAt = deleteAt;
    }
}
