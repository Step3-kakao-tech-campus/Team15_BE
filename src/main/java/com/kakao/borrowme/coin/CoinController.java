package com.kakao.borrowme.coin;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.utils.ApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/payment")
public class CoinController {

    private final CoinService coinService;

    @Autowired
    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }

    // 1. 충전 금액 조회하기
    @GetMapping
    public ResponseEntity<?> getUserCoin(@AuthenticationPrincipal CustomUserDetails userDetails) {
        CoinResponse.FindByIdDTO responseDTO = coinService.getUserCoin(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // 2. 충전하기
    @PostMapping("/charge")
    public ResponseEntity<?> chargeCoin(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CoinRequest.ChargeCoinDTO chargeCoinDTO) {
        CoinResponse.FindByIdDTO responseDTO = coinService.chargeCoin(userDetails.getUser(), chargeCoinDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // 3. 결제하기 - 대여가격 불러오는 방법에 따라 수정 예정
    @PostMapping("/{productId}/create")
    public ResponseEntity<?> useCoin(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CoinRequest.UseCoinDTO useCoinDTO) {
        coinService.useCoin(userDetails.getUser(),useCoinDTO);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success((Object)null);
        return ResponseEntity.ok(apiResult);
    }
}
