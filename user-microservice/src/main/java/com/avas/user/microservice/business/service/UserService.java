package com.avas.user.microservice.business.service;

import com.avas.library.model.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    Optional<UserDTO> findUserById(Long id);
    void deleteUserById(Long id);
    UserDTO createUser(UserDTO newUser);
    UserDTO updateUser(UserDTO modifyExistingUser, Long id);

}
