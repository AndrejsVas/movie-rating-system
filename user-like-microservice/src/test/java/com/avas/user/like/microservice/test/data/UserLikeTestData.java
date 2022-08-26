
package com.avas.user.like.microservice.test.data;

import com.avas.user.like.microservice.business.repository.model.Review;
import com.avas.user.like.microservice.business.repository.model.User;
import com.avas.user.like.microservice.business.repository.model.UserLike;
import com.avas.user.like.microservice.model.UserLikeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UserLikeTestData {

    private static final Random likeId = new Random();
    private static final Random reviewId = new Random();
    private static final Random userId = new Random();

    public static UserLikeDTO createUserLikeDTO() {
        UserLikeDTO userLikeDTO = new UserLikeDTO();
        userLikeDTO.setId(likeId.nextLong());
        userLikeDTO.setUserId(userId.nextLong());
        userLikeDTO.setReviewId(reviewId.nextLong());
        return userLikeDTO;
    }

    public static UserLikeDTO createUserLikeDTOPredefined() {
        UserLikeDTO userLikeDTO = new UserLikeDTO();
        userLikeDTO.setId(1L);
        userLikeDTO.setUserId(1L);
        userLikeDTO.setReviewId(1L);
        return userLikeDTO;
    }

    public static UserLike createUserLike() {
        UserLike userLike = new UserLike();
        userLike.setId(likeId.nextLong());
        userLike.setUserId(new User(userId.nextLong()));
        userLike.setReviewId(new Review(reviewId.nextLong()));
        return userLike;
    }

    public static List<UserLikeDTO> createUserLikeDtoList() {
        return Arrays.asList(createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO());
    }

    public static List<UserLikeDTO> createUserLikeDtoListPredefined() {
        return Arrays.asList(createUserLikeDTOPredefined(), createUserLikeDTOPredefined(), createUserLikeDTOPredefined());
    }

    public static List<UserLike> createUserLikeList() {
        return Arrays.asList(createUserLike(), createUserLike(), createUserLike());
    }
}
