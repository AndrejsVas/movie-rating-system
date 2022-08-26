package com.avas.review.microservice.business.service;

import com.avas.review.microservice.model.UserTypeDTO;

import java.util.List;
import java.util.Optional;

public interface UserTypeService {

    List<UserTypeDTO> getAllUserTypes();

    Optional<UserTypeDTO> findUserTypeById(Long id);

    void deleteUserTypeById(Long id);

    UserTypeDTO createUserType(UserTypeDTO newUserType);

    UserTypeDTO updateUserTypeById(UserTypeDTO modifyExistingUserType, Long id);
}
