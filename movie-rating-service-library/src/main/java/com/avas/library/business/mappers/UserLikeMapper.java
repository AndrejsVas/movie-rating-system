package com.avas.library.business.mappers;

import com.avas.library.business.repository.model.Review;
import com.avas.library.business.repository.model.ReviewLike;
import com.avas.library.business.repository.model.User;
import com.avas.library.model.ReviewLikeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserLikeMapper {
    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdToUserIdLong"),
            @Mapping(source = "reviewId", target = "reviewId", qualifiedByName = "reviewIdToReviewIdLong")
    })
    ReviewLikeDTO mapUserLikeToUserLikeDto(ReviewLike reviewLike);

    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdLongToUserId"),
            @Mapping(source = "reviewId", target = "reviewId", qualifiedByName = "reviewIdLongToReviewId")
    })
    ReviewLike mapUserLikeDtoToUserLike(ReviewLikeDTO userLike);

    List<ReviewLikeDTO> mapUserLikeListToUserLikeDtoList(List<ReviewLike> reviewLike);

    List<ReviewLike> mapUserLikeDtoListToUserLikeList(List<ReviewLikeDTO> userLike);

    @Named("userIdToUserIdLong")
    default Long userIdToUserIdLong(User userId) {
        return userId.getId();
    }

    @Named("userIdLongToUserId")
    default User userIdLongToUserId(Long userId) {
        return new User(userId);
    }

    @Named("reviewIdToReviewIdLong")
    default Long reviewIdToReviewLong(Review reviewId) {
        return reviewId.getId();
    }

    @Named("reviewIdLongToReviewId")
    default Review reviewIdLongToReviewId(Long reviewId) {
        return new Review(reviewId);
    }

}
