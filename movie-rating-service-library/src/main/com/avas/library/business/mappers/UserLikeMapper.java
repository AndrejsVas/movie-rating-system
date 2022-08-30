package main.com.avas.library.business.mappers;

import main.com.avas.library.business.repository.model.Review;
import main.com.avas.library.business.repository.model.User;
import main.com.avas.library.business.repository.model.UserLike;
import main.com.avas.library.model.UserLikeDTO;
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
