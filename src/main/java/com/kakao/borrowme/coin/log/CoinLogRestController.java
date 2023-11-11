package com.kakao.borrowme.coin.log;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.utils.ApiUtils;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment/log")
public class CoinLogRestController {

    private final CoinLogService coinLogService;

    // 1. 충전 금액 조회하기
    @GetMapping("")
    public ResponseEntity<?> getUserCoinLog(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CoinLogResponse.GetUserCoinLogDTO responseDTO = coinLogService.getUserCoinLog(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}