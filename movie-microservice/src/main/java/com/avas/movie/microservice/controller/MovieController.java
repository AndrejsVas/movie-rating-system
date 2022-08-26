package com.avas.movie.microservice.controller;

import com.avas.movie.microservice.business.service.MovieService;
import com.avas.movie.microservice.model.MovieDTO;
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
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movieList = movieService.getAllMovies();
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        Optional<MovieDTO> foundMovie = movieService.findMovieById(id);
        log.info("Movie found : {}", foundMovie.get());
        return new ResponseEntity<>(foundMovie.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieDTO> createMovie(@RequestBody MovieDTO movieDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        MovieDTO savedMovie = movieService.createMovie(movieDto);
        log.debug("New movie is created : {}", movieDto);
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> updateMovie(@PathVariable Long id,
                                              @RequestBody MovieDTO modifiedMovieDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        MovieDTO returnedMovieDTO = movieService.updateMovieById(modifiedMovieDTO , id);
        log.debug("Movie with id: {} is now :{}", id, returnedMovieDTO);
        return new ResponseEntity<>(returnedMovieDTO,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieDTO> deleteMovieById(@PathVariable Long id) {
        log.info("Delete Movie by passing ID, where ID is:{}", id);
        movieService.deleteMovieById(id);
        log.debug("Movie with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
