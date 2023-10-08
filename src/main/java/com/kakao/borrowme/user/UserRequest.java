package com.kakao.borrowme.user;

import com.kakao.borrowme.university.University;
import lombok.Getter;
import lombok.Setter;

public class UserRequest {
    @Getter @Setter
    public static class JoinDTO {
        private String universityName;
        private String email;
        private String password;
        private String nickname;

        public User toEntity() {
            return User.builder()
                    .university(
                            University.builder()
                                    .name(universityName) // Unique가 아니므로, 동일한 대학 무제한 삽입 가능
                                    .build())
                    .email(email)
                    .password(password)
                    .nickname(nickname)
                    .role("student")
                    .build();
        }
    }

    @Getter @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }
}
