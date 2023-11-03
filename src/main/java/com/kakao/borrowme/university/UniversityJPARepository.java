package com.kakao.borrowme.university;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UniversityJPARepository extends JpaRepository<University, Long> {
    Optional<University> findByName(@Param("name") String name);
}
