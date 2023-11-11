package com.kakao.borrowme.coin;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class CoinRestController {
    private final CoinService coinService;

    // 충전 금액 조회하기
    @GetMapping("")
    public ResponseEntity<?> getUserCoin(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CoinResponse.GetUserCoinDTO responseDTO = coinService.getUserCoin(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTO));
    }

    // 충전하기
    @PostMapping("/charge")
    public ResponseEntity<?> chargeCoin(@AuthenticationPrincipal CustomUserDetails userDetails,
                                        @RequestBody CoinRequest.ChargeCoinDTO chargeCoinDTO) {
        coinService.chargeCoin(userDetails.getUser(), chargeCoinDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 결제하기
    @PostMapping("/use-coin/{productId}")
    public ResponseEntity<?> useCoin(@PathVariable Long productId,
                                     @AuthenticationPrincipal CustomUserDetails userDetails,
                                     @RequestBody CoinRequest.UseCoinDTO useCoinDTO) {
        coinService.useCoin(userDetails.getUser(), productId, useCoinDTO);
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
