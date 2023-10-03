package com.kakao.borrowme.company;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company_tb")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_pk")
    private Long id;

    @NotNull
    @Column(length = 45)
    private String name;

    @Column(length = 200)
    private String companyImagePath;

    @Builder
    public Company(Long id, String name, String companyImagePath) {
        this.id = id;
        this.name = name;
        this.companyImagePath = companyImagePath;
    }
}
