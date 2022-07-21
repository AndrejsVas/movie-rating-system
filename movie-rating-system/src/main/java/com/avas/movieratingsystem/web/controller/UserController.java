package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.business.repository.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> userList= userService.getAllUsers();
        if(userList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userList);
    }
}
