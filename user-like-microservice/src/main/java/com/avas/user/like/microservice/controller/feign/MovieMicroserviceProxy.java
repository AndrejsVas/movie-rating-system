package com.avas.user.like.microservice.controller.feign;

import com.avas.library.model.MovieDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

//TODO: Add environmental variables like url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")

@FeignClient(name= "movie-microservice", url = "http://localhost:8200")
public interface MovieMicroserviceProxy {

    @GetMapping("/api/v1/movie/{movieId}")
    public Optional<MovieDTO> getMovie(@PathVariable Long movieId);
}
