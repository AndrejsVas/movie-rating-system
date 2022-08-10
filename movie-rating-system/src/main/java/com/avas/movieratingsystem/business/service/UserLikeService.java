package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserLikeDTO;

import java.util.List;

public interface UserLikeService {

    List<UserLikeDTO> getAllUserLikes(Long userId);
    void likeAReview(Long reviewId);
    void dislikeAReview(Long reviewId);
    List<ReviewDTO> getAllLikesForAReview(Long reviewId);

}
