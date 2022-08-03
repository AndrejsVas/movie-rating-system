package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> userList = userService.getAllUsers();
        if (userList.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> foundUserDto = userService.findUserById(id);
        if (!foundUserDto.isPresent()) {
            log.warn("User not found");
            return ResponseEntity.notFound().build();
        }
        log.info("User found : {}", foundUserDto.get());
        return new ResponseEntity<>(foundUserDto.get(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        UserDTO savedUser = userService.createUser(userDTO);
        log.debug("New user is created : {}", userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserDTO modifiedUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        if (!userService.checkIfUserExistsById(id)) {
            log.info("User with this id does not exist");
            return ResponseEntity.notFound().build();
        }
        UserDTO returnedUserDto = userService.updateUser(modifiedUserDto , id);
        log.debug("User with id: {} is now :{}", id, returnedUserDto);
        return new ResponseEntity<>(returnedUserDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> deleteUserById(Long id) {
        log.info("Delete User  by passing ID, where ID is:{}", id);
        Optional<UserDTO> userDtoFound = userService.findUserById(id);
        if (!(userDtoFound.isPresent())) {
            log.warn("User for delete with id {} is not found.", id);
            return ResponseEntity.notFound().build();
        }
        userService.deleteUserById(id);
        log.debug("User with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewDTO>> getAllReviewsMadeByUser(@PathVariable Long id){
        Optional<List<ReviewDTO>> userReviews = userService.getAllReviewsMadeByUserById(id);
        if(userReviews.isPresent()){
            log.info("Returning all user review for user with id:{}", id);
            return new ResponseEntity<>(userReviews.get(), HttpStatus.OK);
        }
        log.warn("User with id:{} is not found", id);
        return ResponseEntity.notFound().build();

    }
    @GetMapping("/{id}/movies")
    public ResponseEntity<List<MovieDTO>> getAllMoviesReviewedByUser(@PathVariable Long id){
        Optional<List<MovieDTO>> movieReviews = userService.getAllMoviesReviewedByUserById(id);
        if(movieReviews.isPresent()){
            log.info("Returning all user review for user with id:{}", id);
            return new ResponseEntity<>(movieReviews.get(), HttpStatus.OK);
        }
        log.warn("User with id:{} is not found", id);
        return ResponseEntity.notFound().build();

    }
}
