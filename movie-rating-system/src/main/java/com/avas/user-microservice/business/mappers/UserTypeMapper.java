package com.avas.movieratingsystem.business.mappers;

import com.avas.movieratingsystem.business.repository.model.UserType;
import com.avas.movieratingsystem.model.UserTypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {

    UserTypeDTO mapUserTypeToUserTypeDto(UserType userTypeEntity);

    UserType mapUserTypeDtoToUserType(UserTypeDTO userTypeDto);

    List<UserTypeDTO> mapUserTypeListToUserTypeListDto(List<UserType> userTypeEntities);

    List<UserType> mapUserTypeDtoListToUserTypeList(List<UserTypeDTO> userTypeEntities);
}
