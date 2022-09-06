package com.avas.frontend.controller;


import com.avas.frontend.controller.feign.MovieMicroserviceProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/v1/user")
public class MainController {

    @Autowired
    private MovieMicroserviceProxy movieMicroserviceProxy;


    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", movieMicroserviceProxy.getMovies());
        return "index";
    }
}
