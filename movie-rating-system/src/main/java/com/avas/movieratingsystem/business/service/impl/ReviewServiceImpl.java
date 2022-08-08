package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceConflict;
import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.ReviewMapping;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.service.ReviewService;
import com.avas.movieratingsystem.model.ReviewDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Log4j2
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewMapping reviewMapping;

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(reviewMapping::mapReviewToReviewDto).collect(Collectors.toList());

    }

    public Optional<ReviewDTO> findReviewById(Long id) {
        Optional<ReviewDTO> reviewDTO = reviewRepository.findById(id)
                .map(review -> reviewMapping.mapReviewToReviewDto(review));
        if (!reviewDTO.isPresent()) {
            log.warn("review with id:{} Not found", id);
            throw new ResourceNotFoundException("review with id:" + id + " does not exist");
        }
        log.info("Found review :{}", reviewDTO);
        return reviewDTO;
    }

    public void deleteReviewById(Long id) {

        reviewRepository.deleteById(id);
        log.info("review with id: {} is deleted", id);
    }

    public ReviewDTO createReview(ReviewDTO newReview) {
        Review review = reviewMapping.mapReviewDtoToReview(newReview);
        boolean movieTypeAlreadyExists = reviewRepository.existsByMovieIdAndUserId(review.getMovieId(), review.getUserId());
        if (movieTypeAlreadyExists) {
            log.warn("Can not create review ,review  already exists");
            throw new ResourceAlreadyExists("Can not create review , review  already exists");
        }
        Review savedReview = reviewRepository.save(reviewMapping.mapReviewDtoToReview(newReview));
        log.info("review is created : {}", savedReview);
        return reviewMapping.mapReviewToReviewDto(savedReview);
    }

    public ReviewDTO updateReviewById(ReviewDTO modifyExistingReview, Long id) {

        Optional<Review> review = reviewRepository.findById(id);
        Review modifiedReview = reviewMapping.mapReviewDtoToReview(modifyExistingReview);
        if (review.isPresent()) {
            ReviewDTO reviewDTO = reviewMapping.mapReviewToReviewDto(review.get());
            if ((reviewDTO.getUserId().equals(modifyExistingReview.getUserId()))
                    && (reviewDTO.getMovieId().equals(modifyExistingReview.getMovieId()))) {
                modifyExistingReview.setId(id);
                Review reviewToReturn = reviewRepository.save(reviewMapping.mapReviewDtoToReview(modifyExistingReview));
                log.info("Review :{} is now :{}", reviewDTO, modifyExistingReview);
                return reviewMapping.mapReviewToReviewDto(reviewToReturn);
            } else {
                log.warn("Can not update review. This movie_id and the user_id don't match previous record");
                throw new ResourceConflict("Can not update review. This movie_id and the user_id don't match previous record");

            }
        } else {
            log.warn("Can't update, user with this id is not found id:{}", id);
            throw new ResourceNotFoundException("Can't update, user with this id is not found");
        }
    }

    public boolean checkIfReviewExistsById(Long id){
        return reviewRepository.existsById(id);
    };

}
