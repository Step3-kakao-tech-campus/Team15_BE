package com.kakao.borrowme.user;

import com.kakao.borrowme._core.errors.exception.Exception401;
import com.kakao.borrowme._core.errors.exception.Exception409;
import com.kakao.borrowme._core.security.JWTProvider;
import com.kakao.borrowme.university.University;
import com.kakao.borrowme.university.UniversityJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class UserService {
    private final UniversityJPARepository universityJPARepository;
    private final UserJPARepository userJPARepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(UserRequest.JoinDTO requestDTO) {
        checkSameEmail(requestDTO.getEmail());

        University university = universityJPARepository.findByName(requestDTO.getUniversityName()).orElseGet(
                () -> universityJPARepository.save(University.builder().name(requestDTO.getUniversityName()).build())
        );
        requestDTO.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        User user = User.builder()
                .university(university)
                .email(requestDTO.getEmail())
                .password(requestDTO.getPassword())
                .nickname(requestDTO.getNickname())
                .build();
        userJPARepository.save(user);
    }

    public String login(UserRequest.LoginDTO requestDTO) {
        User user = userJPARepository.findByEmail(requestDTO.getEmail()).orElseThrow(
                () -> new Exception401("인증되지 않은 사용자입니다.", "login_unauthenticated_user")
        );
        if (!passwordEncoder.matches(requestDTO.getPassword(), user.getPassword())) {
            throw new Exception401("인증되지 않은 사용자입니다.", "login_unauthenticated_user");
        }
        return JWTProvider.create(user);
    }

    public void checkSameEmail(String email) {
        userJPARepository.findByEmail(email).ifPresent(
                user -> { throw new Exception409("동일한 이메일이 존재합니다.:" + user.getEmail(), "join_duplicated_email"); }
        );
    }
}
