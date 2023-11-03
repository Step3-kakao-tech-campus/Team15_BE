package com.kakao.borrowme.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {
    @Getter @Setter
    public static class JoinDTO {
        @Pattern(regexp = "^[가-힣]+대학교$")
        private String universityName;
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")
        private String email;
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)\\S*$")
        private String password;
        private String nickname;
    }

    @Getter @Setter
    public static class JoinCheckDTO {
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")
        private String email;
    }

    @Getter @Setter
    public static class LoginDTO {
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$")
        private String email;
        @Size(min = 8, max = 20)
        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)\\S*$")
        private String password;
    }
}
