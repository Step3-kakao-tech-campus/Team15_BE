package com.kakao.borrowme.rental;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class RentalRestController {

    private final RentalService rentalService;

    // 대여 내역 조회
    @GetMapping("/rental")
    public ResponseEntity<?> getRental(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<RentalResponse.getRentalDTO> responseDTOs = rentalService.getRental(userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }

    // 반납하기
    @PostMapping("/rental/{rentalId}/return")
    public ResponseEntity<?> returnRental(@PathVariable Long rentalId, @AuthenticationPrincipal CustomUserDetails userDetails) {
        rentalService.returnRental(rentalId, userDetails.getUser());
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success((Object) null);
        return ResponseEntity.ok(apiResult);
    }
}
