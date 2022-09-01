package com.avas.user.like.microservice.controller.feign;

import com.avas.library.model.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//TODO: Add environmental variables like url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")

@FeignClient(name= "user-microservice", url = "http://localhost:8300")
public interface UserMicroserviceProxy {

//    @GetMapping("/api/v1/user/{userId}")
//    public UserDTO getUser(@PathVariable Long userId);
//
    @GetMapping("/api/v1/user/")
    public List<UserDTO> getUser(@PathVariable Long userId);
}
