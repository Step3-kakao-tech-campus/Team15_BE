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
    }

    @Getter @Setter
    public static class LoginDTO {
        private String email;
        private String password;
    }
}
