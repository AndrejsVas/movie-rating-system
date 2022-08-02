package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.UserNotFoundException;
import com.avas.movieratingsystem.business.mappers.UserMapping;
import com.avas.movieratingsystem.business.repository.MovieRepository;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.model.UserReviewDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;
    @Autowired
    ReviewRepository reviewRepository;


    @Autowired
    UserMapping userMapper;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapUserToUserDto).collect(Collectors.toList());

    }

    public Optional<UserDTO> findUserById(Long id) {
        Optional<UserDTO> foundUserDto = userRepository.findById(id)
                .map(foundUser -> userMapper.mapUserToUserDto(foundUser));
        if (foundUserDto.isPresent())
            log.info("Found user :{}", foundUserDto);
        else {
            log.warn("User with id:{} Not found", id);
        }
        return foundUserDto;
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
        log.info("User with id: {} is deleted", id);
    }

    public UserDTO createUser(UserDTO userDTO) {
        //TODO Add More Logic
        User savedUser = userRepository.save(userMapper.mapUserDtoToUser(userDTO));
        log.info("User is created : {}", userDTO);
        return userMapper.mapUserToUserDto(savedUser);
    }

    public UserDTO updateUserById(UserDTO modifyExistingUser, Long id) {

        Optional<User> modifiedFoundUser = userRepository.findById(id)
                .map(foundUser -> {
                    foundUser.setEmail(modifyExistingUser.getEmail());
                    foundUser.setName(modifyExistingUser.getName());
                    foundUser.setUserType(modifyExistingUser.getUserType());
                    foundUser.setSurname(modifyExistingUser.getSurname());
                    return foundUser;
                });
        userRepository.save(modifiedFoundUser.get());
        log.info("User is not updated user id :{}, user is now :{}", id, modifiedFoundUser);
        return userMapper.mapUserToUserDto(modifiedFoundUser.get());
    }

    public List<MovieDTO> getAllMoviesReviewedByUserById(Long id) {
        return null;
    }

    public Optional<List<UserReviewDTO>> getAllReviewsMadeByUserById(Long id) {
        if (!checkIfUserExistsById(id)) {
            log.warn("User with id: {} does not exist", id);
            throw new UserNotFoundException("User with id: {} does not exist");
        }
        List<Review> listReview = reviewRepository.findReviewByUserId(new User(id));
        return Optional.of(userMapper.mapReviewListToUserReviewDto((listReview)));
    }

    public boolean checkIfUserExistsById(Long id) {
        return userRepository.existsById(id);
    }
}
