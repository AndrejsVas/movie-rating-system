package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.UserDTO;

import java.util.List;

public interface UserService {

    List<UserDTO> getAllUsers();
}
