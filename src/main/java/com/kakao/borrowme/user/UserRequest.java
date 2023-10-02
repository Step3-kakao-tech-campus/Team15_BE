package com.kakao.borrowme.user;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserRequest {

    // 요청 유효성 검사 DTO

    public static class EmailCheckDTO {

        private
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        String email;

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

    }

    public static class JoinDTO {
        private
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        String email;

        private
        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$",
                message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다."
        ) String password;

        private @NotEmpty String nickname;

        private @NotEmpty String role;

        public User toEntity() {
            return User.builder().email(this.email).password(this.password).nickname(this.nickname).role(this.role).build();
        }

        public String getEmail() {
            return this.email;
        }

        public String getPassword() {
            return this.password;
        }

        public String getNickname() {
            return this.nickname;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class LoginDTO {
        private
        @NotEmpty
        @Pattern(regexp = "^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,6}$", message = "이메일 형식으로 작성해주세요")
        String email;

        private
        @NotEmpty
        @Size(min = 8, max = 20, message = "8에서 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=!~`<>,./?;:'\"\\[\\]{}\\\\()|_-])\\S*$",
                message = "영문, 숫자, 특수문자가 포함되어야하고 공백이 포함될 수 없습니다."
        ) String password;

        public String getEmail() {
            return this.email;
        }

        public String getPassword() {
            return this.password;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
