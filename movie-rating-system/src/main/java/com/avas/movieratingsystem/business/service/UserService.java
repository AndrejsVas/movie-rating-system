package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.model.UserReviewDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<UserDTO> getAllUsers();
    Optional<UserDTO> findUserById(Long id);
    void deleteUserById(Long id);
    UserDTO createUser(UserDTO newUser);
    UserDTO updateUserById(UserDTO modifyExistingUser, Long id);
    public boolean checkIfUserExistsById(Long id);

    List<MovieDTO> getAllMoviesReviewedByUserById(Long id);
    List<UserReviewDTO> getAllReviewsMadeByUserById(Long id);

}
