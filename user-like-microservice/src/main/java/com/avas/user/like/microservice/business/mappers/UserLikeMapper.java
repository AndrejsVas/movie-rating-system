package com.avas.user.like.microservice.business.mappers;

import com.avas.user.like.microservice.business.repository.model.Review;
import com.avas.user.like.microservice.business.repository.model.User;
import com.avas.user.like.microservice.business.repository.model.UserLike;
import com.avas.user.like.microservice.model.UserLikeDTO;
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
    UserLikeDTO mapUserLikeToUserLikeDto(UserLike userLike);

    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdLongToUserId"),
            @Mapping(source = "reviewId", target = "reviewId", qualifiedByName = "reviewIdLongToReviewId")
    })
    UserLike mapUserLikeDtoToUserLike(UserLikeDTO userLike);

    List<UserLikeDTO> mapUserLikeListToUserLikeDtoList(List<UserLike> userLike);

    List<UserLike> mapUserLikeDtoListToUserLikeList(List<UserLikeDTO> userLike);

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
