package com.avas.movieratingsystem.web.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
@RequestMapping("api/v1/user")
public class ReviewController {



    @GetMapping("/{id}/movie/{movid}/review/{revid}")
    public ResponseEntity<Object> test(@PathVariable Long id, @PathVariable Long movid, @PathVariable Long revid){
        return ResponseEntity.ok().build();
    }

}
