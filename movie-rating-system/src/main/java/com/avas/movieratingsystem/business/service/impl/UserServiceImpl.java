package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceConflict;
import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.MovieMapping;
import com.avas.movieratingsystem.business.mappers.ReviewMapping;
import com.avas.movieratingsystem.business.mappers.UserMapping;
import com.avas.movieratingsystem.business.repository.MovieRepository;
import com.avas.movieratingsystem.business.repository.ReviewRepository;
import com.avas.movieratingsystem.business.repository.UserRepository;
import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.MovieDTO;
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
    @Autowired
    MovieMapping movieMapping;

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

    public Optional<List<MovieDTO>> getAllMoviesReviewedByUserById(Long id) {
        User user = userMapper.mapUserDtoToUser(findUserById(id).get());
        List<Movie> listOfMovies = reviewRepository.findReviewByUserId(new User(id))
                .stream().map(Review::getMovieId).collect(Collectors.toList());
        log.info("List of Movies reviewed by user size is :{}",listOfMovies.size());
        return Optional.of(movieMapping.mapMovieListToMovieListDto((listOfMovies)));

    }

    public Optional<List<ReviewDTO>> getAllReviewsMadeByUserById(Long id) {
        User user = userMapper.mapUserDtoToUser(findUserById(id).get());
        List<Review> listReview = reviewRepository.findReviewByUserId(new User(id));
        log.info("List of reviews by user size is :{}",listReview.size());
        return Optional.of(reviewMapping.mapReviewListToReviewListDto((listReview)));
    }

}
