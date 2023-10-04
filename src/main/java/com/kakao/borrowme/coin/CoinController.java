package com.kakao.borrowme.coin;

import com.kakao.borrowme.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    public ResponseEntity<String> chargeCoin(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CoinRequest coinRequest) {
        CoinResponse.FindByIdDTO responseDTO = coinService.chargeCoin(userDetails.getUser(), coinRequest.getPiece());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }

    // 3. 결제하기
    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CoinRequest coinRequest) {
        CoinResponse.FindByIdDTO responseDTO = coinService.useCoin(userDetails.getUser(), coinRequest.getPiece());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTO);
        return ResponseEntity.ok(apiResult);
    }
}
