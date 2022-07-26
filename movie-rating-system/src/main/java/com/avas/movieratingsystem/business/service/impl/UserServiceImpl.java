package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.mappers.UserMapping;
import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapping userMapper;

    public List<UserDTO> getAllUsers(){
        return userRepository.findAll().stream().map(userMapper::mapUserToUserDto).collect(Collectors.toList());

    }

    public Optional<UserDTO> findUserById(Long id){
        Optional<UserDTO> foundUserDto = userRepository.findById(id)
                .map(foundUser -> userMapper.mapUserToUserDto(foundUser));
        return foundUserDto;
    }
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    };

    public UserDTO createUser(){
        return null;
    }
    public UserDTO updateUserById(UserDTO modifyExistingUser, Long id){
        //TODO: Implement
        return null;
    };
}
