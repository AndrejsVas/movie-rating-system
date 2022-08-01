package com.avas.movieratingsystem.business.mappers;

import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapping {
    ReviewDTO mapReviewToReviewDto(Review reviewEntity);
    Review mapReviewDtoToReview(ReviewDTO reviewDto);

    List<ReviewDTO> mapReviewListToReviewListDto(List<Review> reviewEntities);
}
