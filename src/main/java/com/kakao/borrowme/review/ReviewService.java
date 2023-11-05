package com.kakao.borrowme.review;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewJPARepository reviewRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponse.ReviewDTO> getReview(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        List<ReviewResponse.ReviewDTO> responseDTOs = reviews.stream()
                .map(review -> new ReviewResponse.ReviewDTO(review))
                .collect(Collectors.toList());

        return responseDTOs;
    }
}
