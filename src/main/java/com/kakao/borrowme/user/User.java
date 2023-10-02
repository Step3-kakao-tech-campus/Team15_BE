package com.kakao.borrowme.user;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
//import java.util.ArrayList;
//import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 300, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String nickname;

    @ColumnDefault("'customer'") // default = customer
    private String role;

    @Column(length = 200)
    private String idCardImagePath;

    @Column(length = 200)
    private String profileImagePath;

    @Builder
    public User(int id, String nickname, String email, String password, String role, String idCardImagePath, String profileImagePath){
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.idCardImagePath = idCardImagePath;
        this.profileImagePath = profileImagePath;
    }
}
