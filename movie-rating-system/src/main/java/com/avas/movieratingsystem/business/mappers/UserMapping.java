package com.avas.movieratingsystem.business.mappers;


import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {
    UserDTO mapUserToUserDto(User UserEntity);
    User mapUserDtoToUser(UserDTO userDto);

    List<UserDTO> mapUserListToUserDto(List<User> userEntities);

}
