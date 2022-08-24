package com.avas.review.microservice.business.service;

import com.avas.review.microservice.model.ReviewDTO;
import com.avas.review.microservice.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> findReviewById(Long id);
    void deleteReviewById(Long id);
    ReviewDTO createReview(ReviewDTO newReview);
    ReviewDTO updateReviewById(ReviewDTO modifyExistingReview, Long id);
    List<ReviewDTO> getAllReviewsMadeByUserById(Long userId);
}
