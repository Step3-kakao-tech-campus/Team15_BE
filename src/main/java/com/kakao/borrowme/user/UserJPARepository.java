package com.kakao.borrowme.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(@Param("email") String email);
}
