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

    public UserDTO createUser(UserDTO userDTO){
        User savedUser = userRepository.save(userMapper.mapUserDtoToUser(userDTO));
        return userMapper.mapUserToUserDto(savedUser);
    }
    public UserDTO updateUserById(UserDTO modifyExistingUser, Long id){
        Optional<UserDTO> foundUser = userRepository.findById(id).map(user -> userMapper.mapUserToUserDto(user));
        if(foundUser.isPresent()){
            if(foundUser.get().getId() == id){
                userRepository.save(userMapper.mapUserDtoToUser(foundUser.get()));
                //TODO LOG
            }
            return foundUser.get();
        }
        //Make custom exceptions
        return null;
    };
}
