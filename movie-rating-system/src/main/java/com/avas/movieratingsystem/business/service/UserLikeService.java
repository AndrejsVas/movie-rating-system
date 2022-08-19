package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.model.UserLikeDTO;

import java.util.List;
import java.util.Optional;

public interface UserLikeService {

    List<UserLikeDTO> getAllUserLikes(Long userId);

    Optional<UserLikeDTO> toggleReviewLike(Long reviewId, Long reviewerUserId);

    List<UserLikeDTO> getAllLikesForAReview(Long reviewId);

}
