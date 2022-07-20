package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    public List<User> getAllUsers(){
        List<User> allUsers= userRepository.findAll();
        return allUsers;
    }

}
