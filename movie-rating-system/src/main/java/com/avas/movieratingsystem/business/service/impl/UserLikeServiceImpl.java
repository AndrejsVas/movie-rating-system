package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.UserLikeMapper;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.UserLikeRepository;
import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.repository.model.UserLike;
import com.avas.movieratingsystem.business.service.UserLikeService;
import com.avas.movieratingsystem.model.UserLikeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserLikeServiceImpl implements UserLikeService {

    @Autowired
    UserLikeRepository userLikeRepository;
    @Autowired
    UserLikeMapper userLikeMapper;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    UserRepository userRepository;

    public List<UserLikeDTO> getAllUserLikes(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            log.warn("User with id:{} does not exist", userId);
            throw new ResourceNotFoundException("User does not exist");
        }
        return userLikeRepository.findAllByUserId(user.get()).stream()
                .map(userLikeMapper::mapUserLikeToUserLikeDto).collect(Collectors.toList());
    }

    public void toggleReviewLike(Long reviewId, Long reviewerUserId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        Optional<User> user = userRepository.findById(reviewerUserId);
        if (!review.isPresent() || !user.isPresent()) {
            log.warn("invalid reviewer id or review id");
            throw new ResourceNotFoundException("invalid reviewer id or review id");
        }
        if(userLikeRepository.existsByUserIdAndReviewId(user.get(),review.get())){
            log.warn("user:{} disliked review:{}",user.get(),review.get());
            userLikeRepository.delete(new UserLike(user.get(),review.get()));
            return;
        }
        log.warn("user:{} liked review:{}",user.get(),review.get());
        userLikeRepository.save(new UserLike(user.get(), review.get()));
    }

    public List<UserLikeDTO> getAllLikesForAReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if(!review.isPresent()){
            log.warn("Review with id:{} does not exist", reviewId);
            throw new ResourceNotFoundException("Review does not exist");
        }
        return userLikeRepository.findAllByReviewId(review.get()).stream()
                .map(userLikeMapper::mapUserLikeToUserLikeDto).collect(Collectors.toList());
    }

}
