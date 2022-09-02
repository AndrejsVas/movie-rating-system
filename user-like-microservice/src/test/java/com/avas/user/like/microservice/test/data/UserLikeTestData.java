
package com.avas.user.like.microservice.test.data;

import com.avas.library.business.repository.model.Review;
import com.avas.library.business.repository.model.ReviewLike;
import com.avas.library.business.repository.model.User;
import com.avas.library.model.ReviewLikeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserLikeTestData {

    private static final Random likeId = new Random();
    private static final Random reviewId = new Random();
    private static final Random userId = new Random();

    public static ReviewLikeDTO createUserLikeDTO() {
        ReviewLikeDTO reviewLikeDTO = new ReviewLikeDTO();
        reviewLikeDTO.setId(likeId.nextLong());
        reviewLikeDTO.setUserId(userId.nextLong());
        reviewLikeDTO.setReviewId(reviewId.nextLong());
        return reviewLikeDTO;
    }

    public static ReviewLikeDTO createUserLikeDTOPredefined() {
        ReviewLikeDTO reviewLikeDTO = new ReviewLikeDTO();
        reviewLikeDTO.setId(1L);
        reviewLikeDTO.setUserId(1L);
        reviewLikeDTO.setReviewId(1L);
        return reviewLikeDTO;
    }

    public static ReviewLike createUserLike() {
        ReviewLike reviewLike = new ReviewLike();
        reviewLike.setId(likeId.nextLong());
        reviewLike.setUserId(new User(userId.nextLong()));
        reviewLike.setReviewId(new Review(reviewId.nextLong()));
        return reviewLike;
    }

    public static List<ReviewLikeDTO> createUserLikeDtoList() {
        return Arrays.asList(createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO());
    }

    public static List<ReviewLikeDTO> createUserLikeDtoListPredefined() {
        return Arrays.asList(createUserLikeDTOPredefined(), createUserLikeDTOPredefined(), createUserLikeDTOPredefined());
    }

    public static List<ReviewLike> createUserLikeList() {
        return Arrays.asList(createUserLike(), createUserLike(), createUserLike());
    }
}
