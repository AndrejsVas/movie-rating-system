package com.avas.usertype.microservice.business.service;

import main.java.com.avas.library.model.UserTypeDTO;

import java.util.List;
import java.util.Optional;

public interface UserTypeService {

    List<UserTypeDTO> getAllUserTypes();

    Optional<UserTypeDTO> findUserTypeById(Long id);

    void deleteUserTypeById(Long id);

    UserTypeDTO createUserType(UserTypeDTO newUserType);

    UserTypeDTO updateUserTypeById(UserTypeDTO modifyExistingUserType, Long id);
}
