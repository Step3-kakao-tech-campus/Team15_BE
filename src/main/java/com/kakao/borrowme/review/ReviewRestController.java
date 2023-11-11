package com.kakao.borrowme.review;

import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewRestController {
    private final ReviewService reviewService;

    // 리뷰 조회
    @GetMapping("/{productId}")
    public ResponseEntity<?> getReview(@PathVariable Long productId) {
        List<ReviewResponse.ReviewDTO> responseDTOs = reviewService.getReview(productId);
        return ResponseEntity.ok(ApiUtils.success(responseDTOs));
    }

    // 리뷰 등록
    @PostMapping("/{rentalId}")
    public ResponseEntity<?> postReview(@PathVariable Long rentalId,
                                        @RequestBody ReviewRequest.ReviewDTO requestDTO,
                                        @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.postReview(rentalId, requestDTO, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }

    // 리뷰 삭제
    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId,
                                          @AuthenticationPrincipal CustomUserDetails userDetails) {
        reviewService.deleteReview(reviewId, userDetails.getUser());
        return ResponseEntity.ok(ApiUtils.success(null));
    }
}
