package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.UserNotFoundException;
import com.avas.movieratingsystem.business.mappers.ReviewMapping;
import com.avas.movieratingsystem.business.mappers.UserMapping;
import com.avas.movieratingsystem.business.repository.MovieRepository;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.MoviesByUserDTO;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;
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
    @Autowired
    ReviewMapping reviewMapping;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::mapUserToUserDto).collect(Collectors.toList());

    }

    public Optional<UserDTO> findUserById(Long id) {
        Optional<UserDTO> foundUserDto = userRepository.findById(id)
                .map(foundUser -> userMapper.mapUserToUserDto(foundUser));
        if (!foundUserDto.isPresent()) {
            log.warn("User with id:{} Not found", id);
            throw new UserNotFoundException("User with id:" + id + " does not exist");
        }
        log.info("Found user :{}", foundUserDto);
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
        User modifiedFoundUser = userRepository.save(userMapper.mapUserDtoToUser(modifyExistingUser));
        log.info("User is not updated user id :{}, user is now :{}", id, modifiedFoundUser);
        return userMapper.mapUserToUserDto(modifiedFoundUser);
    }

    public Optional<List<MoviesByUserDTO>> getAllMoviesReviewedByUserById(Long id) {
        User user = userMapper.mapUserDtoToUser(findUserById(id).get());
        List<Movie> listOfMovies = reviewRepository.findReviewByUserId(new User(id))
                .stream().map(Review::getMovieId).collect(Collectors.toList());
        log.info("List of Movies reviewed by user size is :{}",listOfMovies.size());
        return Optional.of(userMapper.mapMovieListForUserToMovieListDto((listOfMovies)));

    }

    public Optional<List<ReviewDTO>> getAllReviewsMadeByUserById(Long id) {
        User user = userMapper.mapUserDtoToUser(findUserById(id).get());
        List<Review> listReview = reviewRepository.findReviewByUserId(new User(id));
        log.info("List of reviews by user size is :{}",listReview.size());
        return Optional.of(reviewMapping.mapReviewListToReviewListDto((listReview)));
    }

    public boolean checkIfUserExistsById(Long id) {
        return userRepository.existsById(id);
    }
}
