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


@Log4j2
@Service
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ReviewMapping reviewMapping;

    public List<ReviewDTO> getAllReviews() {
        List<Review> returnedReviewList = reviewRepository.findAll();
        if (returnedReviewList.isEmpty())
            throw new ResourceNotFoundException("No reviews found");
        log.info("movie list size is :{}", returnedReviewList.size());
        return reviewMapping.mapReviewListToReviewListDto(returnedReviewList);

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
