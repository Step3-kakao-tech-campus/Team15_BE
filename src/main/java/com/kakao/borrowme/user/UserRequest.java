package com.kakao.borrowme.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

public class UserRequest {
    @Getter @Setter
    public static class JoinDTO {
        private String universityName;
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")
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
