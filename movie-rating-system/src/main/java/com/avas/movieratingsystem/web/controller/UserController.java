package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

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
}
