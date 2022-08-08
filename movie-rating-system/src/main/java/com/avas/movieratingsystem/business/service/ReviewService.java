package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.model.ReviewDTO;

import java.util.List;
import java.util.Optional;

public interface ReviewService {

    List<ReviewDTO> getAllReviews();
    Optional<ReviewDTO> findReviewById(Long id);
    void deleteReviewById(Long id);
    ReviewDTO createReview(ReviewDTO newReview);
    ReviewDTO updateReviewById(ReviewDTO modifyExistingReview, Long id);
    boolean checkIfReviewExistsById(Long id);
}
