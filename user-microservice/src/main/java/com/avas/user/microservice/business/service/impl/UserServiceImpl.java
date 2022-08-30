package com.avas.user.microservice.business.service.impl;

import com.avas.user.microservice.business.repository.UserRepository;
import com.avas.user.microservice.business.service.UserService;
import lombok.extern.log4j.Log4j2;
import main.com.avas.library.business.exceptions.ResourceAlreadyExists;
import main.com.avas.library.business.exceptions.ResourceConflict;
import main.com.avas.library.business.exceptions.ResourceNotFoundException;
import main.com.avas.library.business.mappers.UserMapping;
import main.com.avas.library.business.repository.model.User;
import main.com.avas.library.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapping userMapper;


    public List<UserDTO> getAllUsers() {
        List<User> returnedUserList = userRepository.findAll();
        if (returnedUserList.isEmpty())
            throw new ResourceNotFoundException("No users found");
        log.info("user list size is :{}", returnedUserList.size());
        return userMapper.mapUserListToUserDto(returnedUserList);


    }

    public Optional<UserDTO> findUserById(Long id) {
        Optional<UserDTO> foundUserDto = userRepository.findById(id)
                .map(foundUser -> userMapper.mapUserToUserDto(foundUser));
        foundUserDto.orElseThrow(() -> new ResourceNotFoundException("user with id:{0} does not exist", id));
        log.info("Found user :{}", foundUserDto);
        return foundUserDto;
    }

    public void deleteUserById(Long id) {
        findUserById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User for delete with id {0} is not found.", id));
        userRepository.deleteById(id);
        log.info("User with id: {} is deleted", id);
    }

    public UserDTO createUser(UserDTO userDTO) {
        boolean userAlreadyExists = userRepository.existsByEmail(userDTO.getEmail());
        if(userAlreadyExists){
            throw new ResourceAlreadyExists("Can not create user, user with this email already exists");
        }
        User savedUser = userRepository.save(userMapper.mapUserDtoToUser(userDTO));
        log.info("User is created : {}", userDTO);
        return userMapper.mapUserToUserDto(savedUser);
    }

    public UserDTO updateUser(UserDTO modifyExistingUser,Long id) {
        if(!userRepository.existsById(id))
            throw new ResourceNotFoundException("User with id:{0} is not found", id);
        if(userRepository.existsByEmail(modifyExistingUser.getEmail())){
            throw new ResourceConflict("Can not update user. This email:" +modifyExistingUser.getEmail()+
                    " is already taken");
        }
        modifyExistingUser.setId(id);
        User modifiedFoundUser = userRepository.save(userMapper.mapUserDtoToUser(modifyExistingUser));
        log.info("User is updated user id :{}, user is now :{}", modifiedFoundUser.getId(), modifiedFoundUser);
        return userMapper.mapUserToUserDto(modifiedFoundUser);
    }





}
