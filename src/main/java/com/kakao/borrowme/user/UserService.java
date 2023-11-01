package com.kakao.borrowme.user;

import com.kakao.borrowme._core.errors.exception.Exception409;
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
                () -> new Exception409("동일한 이메일이 존재합니다.:" + email, "ckeck_existed_email")
        );
    }
}
