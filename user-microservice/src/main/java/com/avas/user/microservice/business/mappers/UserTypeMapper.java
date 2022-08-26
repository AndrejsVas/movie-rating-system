package com.avas.user.microservice.business.mappers;

import com.avas.user.microservice.business.repository.model.UserType;
import com.avas.user.microservice.model.UserTypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserTypeMapper {

    UserTypeDTO mapUserTypeToUserTypeDto(UserType userTypeEntity);

    UserType mapUserTypeDtoToUserType(UserTypeDTO userTypeDto);

    List<UserTypeDTO> mapUserTypeListToUserTypeListDto(List<UserType> userTypeEntities);

    List<UserType> mapUserTypeDtoListToUserTypeList(List<UserTypeDTO> userTypeEntities);
}
