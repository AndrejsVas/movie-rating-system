package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.model.UserLikeDTO;

import java.util.List;

public interface UserLikeService {

    List<UserLikeDTO> getAllUserLikes(Long userId);
    void toggleReviewLike(Long reviewId, Long reviewerUserId);
    List<UserLikeDTO> getAllLikesForAReview(Long reviewId);

}
