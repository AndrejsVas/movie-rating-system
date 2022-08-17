
package com.avas.movieratingsystem.test.data;

import com.avas.movieratingsystem.model.UserLikeDTO;

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

    public static List<UserLikeDTO> createUserLikeDtoList() {
        return Arrays.asList(createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO(), createUserLikeDTO());
    }
}
