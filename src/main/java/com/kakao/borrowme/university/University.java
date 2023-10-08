package com.kakao.borrowme.university;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "university_tb")
public class University {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "university_pk")
    private Long id;

    @NotNull
    @Column(length = 45)
    private String name;

    @Builder
    public University(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
