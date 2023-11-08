package com.kakao.borrowme.review;

import com.kakao.borrowme._core.errors.exception.Exception400;
import com.kakao.borrowme._core.errors.exception.Exception404;
import com.kakao.borrowme.rental.Rental;
import com.kakao.borrowme.rental.RentalJPARepository;
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
    private final RentalJPARepository rentalRepository;

    @Transactional(readOnly = true)
    public List<ReviewResponse.ReviewDTO> getReview(Long productId) {
        List<Review> reviews = reviewRepository.findAllByProductId(productId);

        List<ReviewResponse.ReviewDTO> responseDTOs = reviews.stream()
                .map(review -> new ReviewResponse.ReviewDTO(review))
                .collect(Collectors.toList());

        return responseDTOs;
    }

    @Transactional
    public void postReview(Long rentalId, ReviewRequest.ReviewDTO requestDTO, User user) {
        Rental rental = rentalRepository.findById(rentalId).orElseThrow(
                () -> new Exception404("존재하지 않는 대여 기록입니다. : " + rentalId, "rental_not_existed")
        );

        int star = requestDTO.getStar();
        if (star < 0 || star > 5) {
            throw new Exception400("별점은 0에서 5 사이의 값으로 작성해주세요." + star, "review_star_length");
        }

        String content = requestDTO.getContent();
        if (content != null && content.length() > 150) {
            throw new Exception400("리뷰 내용은 150자 이내로 작성해주세요." + content, "review_content_length");
        }

        Review review = new Review();
        review.updateProduct(rental.getProduct());
        review.updateStar(requestDTO.getStar());
        review.updateContent(requestDTO.getContent());

        reviewRepository.save(review);
    }

    @Transactional
    public void deleteReview(Long reviewId, User user) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new Exception404("존재하지 않는 리뷰입니다. : " + reviewId, "review_not_existed")
        );
        reviewRepository.delete(review);
    }
}