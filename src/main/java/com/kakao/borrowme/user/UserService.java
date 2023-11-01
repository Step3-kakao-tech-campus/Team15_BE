package com.kakao.borrowme.user;

import com.kakao.borrowme._core.errors.exception.Exception400;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserJPARepository userJPARepository;

    public void sameCheckEmail(String email) {
        userJPARepository.findByEmail(email).orElseThrow(
                () -> new Exception400("동일한 이메일이 존재합니다.:" + email, "join_duplicated_email")
        );
    }
}
