package com.kakao.borrowme.review;

import com.kakao.borrowme._core.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 조회
    @GetMapping("/review/{productId}")
    public ResponseEntity<?> getReview(@PathVariable Long productId) {
        List<ReviewResponse.ReviewDTO> responseDTOs = reviewService.getReview(productId);
        ApiUtils.ApiResult<?> apiResult = ApiUtils.success(responseDTOs);
        return ResponseEntity.ok(apiResult);
    }
}
