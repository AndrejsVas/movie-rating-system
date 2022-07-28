package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.UserService;
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
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userList= userService.getAllUsers();
        if(userList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
        Optional<UserDTO> foundUserDto = userService.findUserById(id);
        if(!foundUserDto.isPresent()){
            log.warn("User not found");
            return ResponseEntity.notFound().build();
        }
        log.info("User found : {}", foundUserDto.get());
        return new ResponseEntity<>(foundUserDto.get(),HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        userService.createUser(userDTO);
        log.debug("New user is created : {}",userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED );

    }
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(){
        return null;
    }
    @DeleteMapping
    public ResponseEntity<UserDTO> deleteUser(){
        return null;

    }
}
