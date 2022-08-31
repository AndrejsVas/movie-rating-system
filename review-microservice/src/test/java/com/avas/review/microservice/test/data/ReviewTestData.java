package com.avas.review.microservice.test.data;

import main.java.com.avas.library.model.ReviewDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReviewTestData {

    private static final String[] textReviewRandomList = {"good", "bad", "decent", "better call soul", "outsanding"};

    private static final Random reviewId = new Random();
    private static final Random textReview = new Random();
    private static final Random movieId = new Random();
    private static final Random userId = new Random();
    private static final Random score = new Random();

    public static ReviewDTO createReviewDto() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(reviewId.nextLong());
        reviewDTO.setScore(score.nextInt(10));
        reviewDTO.setTextReview(textReviewRandomList[textReview.nextInt(textReviewRandomList.length)]);
        reviewDTO.setMovieId(movieId.nextLong());
        reviewDTO.setUserId(userId.nextLong());
        return reviewDTO;
    }
    public static ReviewDTO createReviewDtoPredefined() {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(1L);
        reviewDTO.setTextReview("best movie since the godfather");
        reviewDTO.setScore(7);
        return reviewDTO;
    }

    public static List<ReviewDTO> createReviewDtoList() {
        return Arrays.asList(createReviewDto(), createReviewDto(), createReviewDto(), createReviewDto(), createReviewDto());
    }

    public static List<ReviewDTO> createReviewDtoListPredefined() {
        return Arrays.asList(createReviewDtoPredefined(), createReviewDtoPredefined(), createReviewDtoPredefined());
    }
}
