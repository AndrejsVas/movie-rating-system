package com.avas.user.like.microservice.business.service;

import com.avas.library.model.MovieDTO;
import com.avas.library.model.MovieLikeDTO;
import com.avas.library.model.UserLikeDTO;

import java.util.List;
import java.util.Optional;

public interface UserLikeService {

    List<MovieLikeDTO> getAllLikesForMovie(MovieDTO movieId);
    List<UserLikeDTO> getAllUserLikes(Long userId);

    Optional<UserLikeDTO> toggleReviewLike(Long reviewId, Long reviewerUserId);

    List<UserLikeDTO> getAllLikesForAReview(Long reviewId);

}
