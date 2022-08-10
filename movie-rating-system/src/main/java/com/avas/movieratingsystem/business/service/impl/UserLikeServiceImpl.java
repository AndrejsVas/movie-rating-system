package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.repository.UserLikeRepository;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserLikeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class UserLikeServiceImpl {

    @Autowired
    UserLikeRepository userLikeRepository;


    public List<UserLikeDTO> getAllUserLikes(Long userId) {
        return null;
    }

    public void likeAReview(Long reviewId) {

    }

    public void dislikeAReview(Long reviewId) {

    }

    public List<ReviewDTO> getAllLikesForAReview(Long reviewId) {
        return null;
    }

}
