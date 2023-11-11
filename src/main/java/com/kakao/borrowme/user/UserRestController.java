package com.kakao.borrowme.user;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.security.JWTProvider;
import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody @Valid UserRequest.JoinDTO requestDTO) {
        userService.join(requestDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/join/check")
    public ResponseEntity<?> joinCheck(@RequestBody @Valid UserRequest.JoinCheckDTO requestDTO) {
        userService.checkSameEmail(requestDTO.getEmail());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO) {
        String jwt = userService.login(requestDTO);

        ResponseCookie responseCookie = ResponseCookie.from("accessToken", jwt.replace(JWTProvider.TOKEN_PREFIX, ""))
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(JWTProvider.ACCESS_EXP)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ApiUtils.success(null));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal CustomUserDetails userDetails) {
        ResponseCookie responseCookie = ResponseCookie.from("accessToken", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ApiUtils.success(null));
    }
}
