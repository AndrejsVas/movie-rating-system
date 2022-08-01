package com.avas.movieratingsystem.business.mappers;

import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.repository.model.UserType;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.model.UserTypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {

    UserTypeDTO mapUserTypeToUserTypeDto(User userTypeEntity);
    UserType mapUserTypeDtoToUserType(UserDTO userTypeDto);

    List<UserTypeDTO> mapUserTypeListToUserTypeListDto(List<UserType> userTypeEntities);
}
