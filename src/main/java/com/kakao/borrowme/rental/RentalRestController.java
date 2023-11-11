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
@RequestMapping("/rental")
public class RentalRestController {
    private final RentalService rentalService;

    // 대여 내역 조회
    @GetMapping("")
    public ResponseEntity<?> getRental(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<RentalResponse.RentalDTO> responseDTOs = rentalService.getRental(userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // 반납하기
    @PostMapping("/{rentalId}/return")
    public ResponseEntity<?> returnRental(@PathVariable Long rentalId,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        rentalService.returnRental(rentalId, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
