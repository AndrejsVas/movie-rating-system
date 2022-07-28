package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "index";
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        List<UserDTO> userList= userService.getAllUsers();
        if(userList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userList);
    }
}
