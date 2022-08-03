package com.avas.movieratingsystem.business.mappers;


import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.repository.model.UserType;
import com.avas.movieratingsystem.model.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {
    @Mappings({
            @Mapping(source = "userType", target = "userType", qualifiedByName = "userTypeToString")
    })
    UserDTO mapUserToUserDto(User UserEntity);
    @Mappings({
            @Mapping(source = "userType", target = "userType", qualifiedByName = "userTypeStringToUserTypeDto")
    })
    User mapUserDtoToUser(UserDTO userDto);
    List<UserDTO> mapUserListToUserDto(List<User> userEntities);

    @Named("userTypeToString")
    default String userUserTypeToUserTypeDto(UserType userType){
        return userType.getType();
    }
    @Named("userTypeStringToUserTypeDto")
    default UserType userUserTypeToUserTypeDto(String userType){
        return new UserType(userType);
    }

}
