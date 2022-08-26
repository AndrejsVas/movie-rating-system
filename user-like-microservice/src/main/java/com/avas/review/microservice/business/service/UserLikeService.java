package com.avas.review.microservice.business.service;

import com.avas.review.microservice.model.UserLikeDTO;

import java.util.List;
import java.util.Optional;

public interface UserLikeService {

    List<UserLikeDTO> getAllUserLikes(Long userId);

    Optional<UserLikeDTO> toggleReviewLike(Long reviewId, Long reviewerUserId);

    List<UserLikeDTO> getAllLikesForAReview(Long reviewId);

}
