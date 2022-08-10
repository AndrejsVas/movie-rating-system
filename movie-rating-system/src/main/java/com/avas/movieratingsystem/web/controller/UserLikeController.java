package com.avas.movieratingsystem.web.controller;


import com.avas.movieratingsystem.business.service.UserLikeService;
import com.avas.movieratingsystem.model.UserLikeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Log4j2
@Controller
@RequestMapping("api/v1/like")
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserLikeDTO>> getAllUserLikes(@PathVariable Long userId) {
        List<UserLikeDTO> userLikes = userLikeService.getAllUserLikes(userId);
        log.info("User with id:{} has {} likes", userId,userLikes.size());
        if (userLikes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userLikes, HttpStatus.OK);
    }






}
