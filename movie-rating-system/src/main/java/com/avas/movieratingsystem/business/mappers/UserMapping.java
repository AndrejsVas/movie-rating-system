package com.avas.movieratingsystem.business.mappers;


import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.model.UserReviewDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {
    UserDTO mapUserToUserDto(User UserEntity);
    User mapUserDtoToUser(UserDTO userDto);
    List<UserReviewDTO> mapReviewListToUserReviewDto(List<Review> userEntities);
    List<UserDTO> mapUserListToUserDto(List<User> userEntities);

}
