package com.avas.movieratingsystem.business.mappers;

import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.repository.model.UserType;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapping {
    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdToUserIdLong")
    })
    ReviewDTO mapReviewToReviewDto(Review reviewEntity);
    @Mappings({
            @Mapping(source = "userId", target = "userId", qualifiedByName = "userIdLongToUserId")
    })
    Review mapReviewDtoToReview(ReviewDTO reviewDto);

    List<ReviewDTO> mapReviewListToReviewListDto(List<Review> reviewEntities);

    @Named("userIdToUserIdLong")
    default Long userIdToUserIdLong(User userId){
        return userId.getId();
    }
    @Named("userIdLongToUserId")
    default User userIdLongToUserId(Long userId){
        return new User(userId);
    }
}
