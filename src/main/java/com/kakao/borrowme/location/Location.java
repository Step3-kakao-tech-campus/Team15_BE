package com.kakao.borrowme.location;

import com.kakao.borrowme.university.University;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "location_tb")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_pk")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "university_pk")
    private University university;

    @NotNull
    private BigDecimal latitude;

    @NotNull
    private BigDecimal longitude;

    @NotNull
    @Column(length = 45)
    private String name;

    @Builder
    public Location(Long id, University university, BigDecimal latitude, BigDecimal longitude, String name) {
        this.id = id;
        this.university = university;
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
    }
}
