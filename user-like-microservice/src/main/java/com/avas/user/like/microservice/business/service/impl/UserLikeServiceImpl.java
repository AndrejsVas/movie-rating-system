package com.avas.user.like.microservice.business.service.impl;

import com.avas.library.business.mappers.MovieLikeMapper;
import com.avas.library.business.mappers.MovieMapping;
import com.avas.library.business.repository.model.Movie;
import com.avas.library.business.repository.model.MovieLike;
import com.avas.library.model.MovieDTO;
import com.avas.library.model.MovieLikeDTO;
import com.avas.user.like.microservice.business.repository.MovieLikeRepository;
import com.avas.user.like.microservice.business.repository.ReviewRepository;
import com.avas.user.like.microservice.business.repository.UserLikeRepository;
import com.avas.user.like.microservice.business.repository.UserRepository;
import com.avas.user.like.microservice.business.service.UserLikeService;
import lombok.extern.log4j.Log4j2;
import com.avas.library.business.exceptions.ResourceAlreadyExists;
import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.business.mappers.ReviewMapping;
import com.avas.library.business.mappers.UserLikeMapper;
import com.avas.library.business.mappers.UserMapping;
import com.avas.library.business.repository.model.UserLike;
import com.avas.library.model.ReviewDTO;
import com.avas.library.model.UserDTO;
import com.avas.library.model.UserLikeDTO;
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
    MovieLikeRepository movieLikeRepository;
    @Autowired
    ReviewMapping reviewMapping;
    @Autowired
    MovieLikeMapper movieLikeMapper;
    @Autowired
    MovieMapping movieMapping;
    @Autowired
    UserMapping userMapping;

    @Override
    public List<MovieLikeDTO> getAllLikesForMovie(MovieDTO movie) {
        List<MovieLike> movieLikeList = movieLikeRepository
                .findMovieLikeByMovieId(movieMapping.mapMovieDtoToMovie(movie));
        if(movieLikeList.isEmpty())
            throw new ResourceNotFoundException("This movie has no likes");
        return movieLikeMapper.mapUserLikeListToUserLikeDtoList(movieLikeList);
    }

    public List<UserLikeDTO> getAllUserLikes(Long userId) {
        Optional<UserDTO> user = userRepository.findById(userId)
                .map(returnedUser -> userMapping.mapUserToUserDto(returnedUser));
        user.orElseThrow(() -> new ResourceNotFoundException("User with id:{0} does not exist", userId));
        List<UserLikeDTO> userLikeDTOList = userLikeRepository
                .findAllByUserId(userMapping.mapUserDtoToUser(user.get())).stream()
                .map(userLikeMapper::mapUserLikeToUserLikeDto).collect(Collectors.toList());
        if (userLikeDTOList.isEmpty())
            throw new ResourceNotFoundException("User with user id:{0} has no likes", userId);
        else
            return userLikeDTOList;
    }

    public Optional<UserLikeDTO> toggleReviewLike(Long reviewId, Long reviewerUserId) {
        Optional<ReviewDTO> reviewDTO = reviewRepository.findById(reviewId)
                .map(review -> reviewMapping.mapReviewToReviewDto(review));
        Optional<UserDTO> foundUserDTO = userRepository.findById(reviewerUserId)
                .map(foundUser -> userMapping.mapUserToUserDto(foundUser));
        if (!reviewDTO.isPresent() || !foundUserDTO.isPresent())
            throw new ResourceAlreadyExists("invalid reviewer id or review id");
        if (userLikeRepository.existsByUserIdAndReviewId(userMapping.mapUserDtoToUser(foundUserDTO.get())
                , reviewMapping.mapReviewDtoToReview(reviewDTO.get()))) {
            log.warn("user:{} disliked review:{}", foundUserDTO.get(), reviewDTO.get());
            userLikeRepository.delete(userLikeRepository.findByUserIdAndReviewId(userMapping.mapUserDtoToUser(foundUserDTO.get())
                    , reviewMapping.mapReviewDtoToReview(reviewDTO.get())).get());
            return Optional.empty();
        }
        log.warn("user:{} liked review:{}", foundUserDTO.get(), reviewDTO.get());
        UserLike userLike = userLikeRepository.save(new UserLike(userMapping.mapUserDtoToUser(foundUserDTO.get())
                , reviewMapping.mapReviewDtoToReview(reviewDTO.get())));
        log.warn("user like:{}", userLike);
        return Optional.of(userLikeMapper.mapUserLikeToUserLikeDto(userLike));
    }

    public List<UserLikeDTO> getAllLikesForAReview(Long reviewId) {
        Optional<ReviewDTO> review = reviewRepository.findById(reviewId)
                .map(reviewMapping::mapReviewToReviewDto);
        review.orElseThrow(() -> new ResourceNotFoundException("Review with id:{0} does not exist", reviewId));
        List<UserLikeDTO> userLikeDTOList = userLikeRepository.findAllByReviewId(reviewMapping
                        .mapReviewDtoToReview(review.get())).stream()
                .map(userLikeMapper::mapUserLikeToUserLikeDto).collect(Collectors.toList());
        if (userLikeDTOList.isEmpty())
            throw new ResourceNotFoundException("Review with review id:{0} has no likes", reviewId);
        else
            return userLikeDTOList;
    }

}
