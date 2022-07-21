package com.avas.movieratingsystem.business.mappers;


import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {
    UserDTO map(User UserEntity);
    User map(UserDTO userDto);

    List<UserDTO> map(List<User> userEntities);

}
