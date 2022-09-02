package com.avas.user.like.microservice.controller;


import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.model.MovieDTO;
import com.avas.library.model.MovieLikeDTO;
import com.avas.library.model.ReviewDTO;
import com.avas.library.model.ReviewLikeDTO;
import com.avas.library.model.UserDTO;
import com.avas.user.like.microservice.business.service.UserLikeService;
import com.avas.user.like.microservice.controller.feign.MovieMicroserviceProxy;
import com.avas.user.like.microservice.controller.feign.ReviewMicroserviceProxy;
import com.avas.user.like.microservice.controller.feign.UserMicroserviceProxy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("api/v1/like")
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

    @Autowired
    UserMicroserviceProxy userMicroserviceProxy;
    @Autowired
    private MovieMicroserviceProxy movieMicroserviceProxy;
    @Autowired
    private ReviewMicroserviceProxy reviewMicroserviceProxy;

    @GetMapping("/movie/{movieId}")
    public ResponseEntity<List<MovieLikeDTO>> getLikesForMovie(@PathVariable Long movieId){
        Optional<MovieDTO> movieDTO = movieMicroserviceProxy.getMovie(movieId);
        movieDTO.orElseThrow(() -> new ResourceNotFoundException("Movie with id {0} is not found", movieId));
        return new ResponseEntity<>(userLikeService.getAllLikesForMovie(movieDTO.get()), HttpStatus.OK);
    }
    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<ReviewLikeDTO>> getAllLikesForReview(@PathVariable Long reviewId) {
        Optional<ReviewDTO> reviewDTO = reviewMicroserviceProxy.getReview(reviewId);
        reviewDTO.orElseThrow(() -> new ResourceNotFoundException("Review with id {0} is not found", reviewId));
        return new ResponseEntity<>(userLikeService.getAllLikesForAReview(reviewDTO.get()), HttpStatus.OK);
    }
    @PutMapping("/movie/{movieId}/user/{userId}")
    public ResponseEntity<MovieLikeDTO> toggleMovieLike(@PathVariable Long movieId, @PathVariable Long userId) {
        Optional<MovieDTO> movieDTO = movieMicroserviceProxy.getMovie(movieId);
        movieDTO.orElseThrow(() -> new ResourceNotFoundException("Movie with id {0} is not found", movieId));
        Optional<UserDTO> userDTO = userMicroserviceProxy.getUser(userId);
        userDTO.orElseThrow(() -> new ResourceNotFoundException("User with id {0} is not found", userId));
        return userLikeService.toggleMovieLike(movieDTO.get(),userDTO.get())
                .map(movieLikeDTO -> new ResponseEntity<>(movieLikeDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }


    @PutMapping("/review/{reviewId}/reviewer/{userId}")
    public ResponseEntity<ReviewLikeDTO> toggleReviewLike(@PathVariable Long reviewId, @PathVariable Long userId) {
        Optional<ReviewDTO> reviewDTO = reviewMicroserviceProxy.getReview(reviewId);
        reviewDTO.orElseThrow(() -> new ResourceNotFoundException("Review with id {0} is not found", reviewId));
        Optional<UserDTO> userDTO = userMicroserviceProxy.getUser(userId);
        userDTO.orElseThrow(() -> new ResourceNotFoundException("User with id {0} is not found", userId));
        return userLikeService.toggleReviewLike(reviewDTO.get(),userDTO.get())
                .map(reviewLikeDTO -> new ResponseEntity<>(reviewLikeDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }



}
