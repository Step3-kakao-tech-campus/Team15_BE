package com.kakao.borrowme.review;

import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme._core.security.CustomUserDetails;
import com.kakao.borrowme.product.Product;
import com.kakao.borrowme.product.ProductJPARepository;
import com.kakao.borrowme.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewJPARepository reviewRepository;
    private final ProductJPARepository productRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponse.ReviewDTO> getReview(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        List<ReviewResponse.ReviewDTO> responseDTOs = reviews.stream()
                .map(review -> new ReviewResponse.ReviewDTO(review))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    @Transactional
    public void postReview(Long productId, ReviewRequest.ReviewDTO requestDTO, User user) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new Exception404("존재하지 않는 제품입니다. : " + productId, "review_not_existed"));

        Review review = new Review();
        review.updateProduct(product);
        review.updateStar(requestDTO.getStar());
        review.updateContent(requestDTO.getContent());

        reviewRepository.save(review);
    }
}
