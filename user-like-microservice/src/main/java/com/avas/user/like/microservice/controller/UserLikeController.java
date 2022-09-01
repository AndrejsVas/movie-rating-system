package com.avas.user.like.microservice.controller;


import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.model.MovieDTO;
import com.avas.library.model.MovieLikeDTO;
import com.avas.library.model.UserLikeDTO;
import com.avas.user.like.microservice.business.service.UserLikeService;
import com.avas.user.like.microservice.controller.feign.MovieMicroserviceProxy;
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

@Log4j2
@Controller
@RequestMapping("api/v1/like")
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

//    @Autowired
//    UserMicroserviceProxy userMicroserviceProxy;
    @Autowired
    private MovieMicroserviceProxy movieMicroserviceProxy;
    @GetMapping("/movie/{movieId}")
    //public ResponseEntity<List<MovieLike>> getLikesForMovie(@PathVariable Long movieId){
    public ResponseEntity<List<MovieLikeDTO>> getLikesForMovie(@PathVariable Long movieId){
        MovieDTO movieDTO = movieMicroserviceProxy.getMovie(movieId)
                .orElseThrow(() -> new ResourceNotFoundException("Movie with id {0} is not found", movieId));
        return new ResponseEntity<>(userLikeService.getAllLikesForMovie(movieDTO), HttpStatus.OK);
    }

    @PutMapping("/review/{reviewId}/reviewer/{userId}")
    public ResponseEntity<UserLikeDTO> toggleReviewLike(@PathVariable Long reviewId, @PathVariable Long userId) {
        return userLikeService.toggleReviewLike(reviewId, userId)
                .map(likeDTO -> new ResponseEntity<>(likeDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserLikeDTO>> getAllUserLikes(@PathVariable Long userId) {
        List<UserLikeDTO> userLikes = userLikeService.getAllUserLikes(userId);
        log.info("User with id:{} has {} likes", userId, userLikes.size());
        return new ResponseEntity<>(userLikes, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<UserLikeDTO>> getAllLikesForReview(@PathVariable Long reviewId) {
        List<UserLikeDTO> userLikes = userLikeService.getAllLikesForAReview(reviewId);
        log.info("Review with id:{} has {} likes", reviewId, userLikes.size());
        return new ResponseEntity<>(userLikes, HttpStatus.OK);
    }



}
