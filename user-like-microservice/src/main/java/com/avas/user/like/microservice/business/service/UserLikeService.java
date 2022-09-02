package com.avas.user.like.microservice.business.service;

import com.avas.library.model.MovieDTO;
import com.avas.library.model.MovieLikeDTO;
import com.avas.library.model.ReviewDTO;
import com.avas.library.model.ReviewLikeDTO;
import com.avas.library.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserLikeService {

    Optional<MovieLikeDTO> toggleMovieLike(MovieDTO movieDTO, UserDTO userDTO);
    Optional<ReviewLikeDTO> toggleReviewLike(ReviewDTO reviewDTO, UserDTO userDTO);

    List<MovieLikeDTO> getAllLikesForMovie(MovieDTO movieId);

    List<ReviewLikeDTO> getAllLikesForAReview(ReviewDTO review);

}
