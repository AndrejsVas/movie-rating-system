package com.avas.movietype.microservice.controller;

import com.avas.movietype.microservice.business.service.MovieTypeService;
import lombok.extern.log4j.Log4j2;
import main.java.com.avas.library.model.MovieTypeDTO;
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
@RequestMapping("api/v1/movie_type")
public class MovieTypeController {

    @Autowired
    MovieTypeService movieTypeService;




    @GetMapping
    public ResponseEntity<List<MovieTypeDTO>> getAllMovieTypes() {
        List<MovieTypeDTO> movieTypeList = movieTypeService.getAllMovieTypes();
        return ResponseEntity.ok(movieTypeList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieTypeDTO> getMovieType(@PathVariable Long id) {
        Optional<MovieTypeDTO> foundMovieType = movieTypeService.findMovieTypeById(id);
        log.info("Movie type found : {}", foundMovieType.get());
        return new ResponseEntity<>(foundMovieType.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<MovieTypeDTO> createMovieType(@RequestBody MovieTypeDTO movieTypeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        MovieTypeDTO savedMovieType = movieTypeService.createMovieType(movieTypeDTO);
        log.debug("New movie type is created : {}", movieTypeDTO);
        return new ResponseEntity<>(savedMovieType, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieTypeDTO> updateMovieType(@PathVariable Long id,
                                                        @RequestBody MovieTypeDTO modifiedMovieTypeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        MovieTypeDTO returnedMovieTypeDTO = movieTypeService.updateMovieTypeById(modifiedMovieTypeDTO, id);
        log.debug("Movie type with id: {} is now :{}", id, returnedMovieTypeDTO);
        return new ResponseEntity<>(returnedMovieTypeDTO, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MovieTypeDTO> deleteMovieTypeById(@PathVariable Long id) {
        log.info("Delete Movie Type by passing ID, where ID is:{}", id);
        movieTypeService.deleteMovieTypeById(id);
        log.debug("Movie type with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
