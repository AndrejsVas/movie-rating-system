package com.avas.review.microservice.business.service.impl;

import com.avas.review.microservice.business.repository.ReviewRepository;
import com.avas.review.microservice.business.service.ReviewService;
import lombok.extern.log4j.Log4j2;
import com.avas.library.business.exceptions.ResourceAlreadyExists;
import com.avas.library.business.exceptions.ResourceConflict;
import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.business.mappers.ReviewMapping;
import com.avas.library.business.mappers.UserMapping;
import com.avas.library.business.repository.model.Review;
import com.avas.library.business.repository.model.User;
import com.avas.library.model.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Log4j2
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;
// TODO: Think how to better UserDTO's, Should they be the same DTO in both microservices, like UserDTO, or a
//TODO: Localized version of UserDTO?
    @Autowired
ReviewMapping reviewMapping;
    //TODO: Should this be in a library? Maybe there is a way to do this without the userMapping import?
    @Autowired
    UserMapping userMapping;

    public List<ReviewDTO> getAllReviews() {
        List<Review> returnedReviewList = reviewRepository.findAll();
        if (returnedReviewList.isEmpty())
            throw new ResourceNotFoundException("No reviews found");
        log.info("movie list size is :{}", returnedReviewList.size());
        return reviewMapping.mapReviewListToReviewListDto(returnedReviewList);

    }

    public List<ReviewDTO> getAllReviewsMadeByUserById(Long userId) {
        List<Review> listReview = reviewRepository.findReviewByUserId(new User(userId));
        if(listReview.isEmpty())
            throw new ResourceNotFoundException("No reviews found for user:{0}", userId);
        log.info("List of reviews by user size is :{}",listReview.size());
        return reviewMapping.mapReviewListToReviewListDto((listReview));
    }

    public Optional<ReviewDTO> findReviewById(Long id) {
        Optional<ReviewDTO> reviewDTO = reviewRepository.findById(id)
                .map(review -> reviewMapping.mapReviewToReviewDto(review));
        reviewDTO.orElseThrow(() -> new ResourceNotFoundException("review with id:{0} does not exist", id));
        log.info("Found review :{}", reviewDTO);
        return reviewDTO;
    }

    public void deleteReviewById(Long id) {
        findReviewById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Review for delete with id {0} is not found.", id));
        reviewRepository.deleteById(id);
        log.info("review with id: {} is deleted", id);
    }

    public ReviewDTO createReview(ReviewDTO newReview) {
        Review review = reviewMapping.mapReviewDtoToReview(newReview);
        boolean reviewAlreadyExists = reviewRepository.existsByMovieIdAndUserId(review.getMovieId(), review.getUserId());
        if (reviewAlreadyExists) {
            throw new ResourceAlreadyExists("Can not create review , review  already exists");
        }
        Review savedReview = reviewRepository.save(reviewMapping.mapReviewDtoToReview(newReview));
        log.info("review is created : {}", savedReview);
        return reviewMapping.mapReviewToReviewDto(savedReview);
    }

    public ReviewDTO updateReviewById(ReviewDTO modifyExistingReview, Long id) {
        Optional<ReviewDTO> reviewDTO = reviewRepository.findById(id)
                .map(foundReview -> reviewMapping.mapReviewToReviewDto(foundReview));
        reviewDTO.orElseThrow(() -> new ResourceNotFoundException("Review with id:{0} is not found", id));
        if ((reviewDTO.get().getUserId().equals(modifyExistingReview.getUserId()))
                && (reviewDTO.get().getMovieId().equals(modifyExistingReview.getMovieId()))) {
            modifyExistingReview.setId(id);
            Review reviewToReturn = reviewRepository.save(reviewMapping.mapReviewDtoToReview(modifyExistingReview));
            log.info("Review :{} is now :{}", reviewDTO, modifyExistingReview);
            return reviewMapping.mapReviewToReviewDto(reviewToReturn);
        } else
            throw new ResourceConflict("Can not update review. This movie_id:{0} and the user_id{1} don't match previous record"
                    , reviewDTO.get().getUserId(), reviewDTO.get().getMovieId());
    }


}
