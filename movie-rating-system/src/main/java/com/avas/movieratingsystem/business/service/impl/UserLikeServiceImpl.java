package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.mappers.ReviewMapping;
import com.avas.movieratingsystem.business.mappers.UserLikeMapper;
import com.avas.movieratingsystem.business.mappers.UserMapping;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.UserLikeRepository;
import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.repository.model.UserLike;
import com.avas.movieratingsystem.business.service.UserLikeService;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.model.UserLikeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    ReviewMapping reviewMapping;
    @Autowired
    UserMapping userMapping;

    public List<UserLikeDTO> getAllUserLikes(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            log.warn("User with id:{} does not exist", userId);
            throw new ResourceAlreadyExists("User does not exist");
        }
        return userLikeRepository.findAllByUserId(user.get()).stream()
                .map(userLikeMapper::mapUserLikeToUserLikeDto).collect(Collectors.toList());
    }

    public void toggleReviewLike(Long reviewId, Long reviewerUserId) {
        Optional<ReviewDTO> reviewDTO = reviewRepository.findById(reviewId)
                .map(review -> reviewMapping.mapReviewToReviewDto(review));
        Optional<UserDTO> foundUserDTO = userRepository.findById(reviewerUserId)
                .map(foundUser -> userMapping.mapUserToUserDto(foundUser));
        if (!reviewDTO.isPresent() || !foundUserDTO.isPresent()) {
            log.warn("invalid reviewer id or review id");
            throw new ResourceAlreadyExists("invalid reviewer id or review id");
        }
        if(userLikeRepository.existsByUserIdAndReviewId(userMapping.mapUserDtoToUser(foundUserDTO.get())
                ,reviewMapping.mapReviewDtoToReview(reviewDTO.get())))
        {
            log.warn("user:{} disliked review:{}",foundUserDTO.get(),reviewDTO.get());
            userLikeRepository.delete(userLikeRepository.findByUserIdAndReviewId(userMapping.mapUserDtoToUser(foundUserDTO.get())
                    ,reviewMapping.mapReviewDtoToReview(reviewDTO.get())).get());
            return;
        }
        log.warn("user:{} liked review:{}",foundUserDTO.get(),reviewDTO.get());
        UserLike userLike = userLikeRepository.save(new UserLike(userMapping.mapUserDtoToUser(foundUserDTO.get())
                , reviewMapping.mapReviewDtoToReview(reviewDTO.get())));
        log.warn("user like:{}",userLike);
    }

    public List<UserLikeDTO> getAllLikesForAReview(Long reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if(!review.isPresent()){
            log.warn("Review with id:{} does not exist", reviewId);
            throw new ResourceAlreadyExists("Review does not exist");
        }
        return userLikeRepository.findAllByReviewId(review.get()).stream()
                .map(userLikeMapper::mapUserLikeToUserLikeDto).collect(Collectors.toList());
    }

}
