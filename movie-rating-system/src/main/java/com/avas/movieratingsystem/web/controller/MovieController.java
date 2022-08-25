package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.MovieService;
import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.swagger.HTMLResponseMessages;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;
@Log4j2
@Controller
@Tag(name="Movie controller", description= "A controller for operations with the movie database")
@RequestMapping("api/v1/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @GetMapping
    @Operation(summary = "Retrieve a list of all movies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = MovieDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<List<MovieDTO>> getAllMovies() {
        List<MovieDTO> movieList = movieService.getAllMovies();
        return ResponseEntity.ok(movieList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MovieDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<MovieDTO> getMovieById(@PathVariable Long id) {
        Optional<MovieDTO> foundMovie = movieService.findMovieById(id);
        log.info("Movie found : {}", foundMovie.get());
        return new ResponseEntity<>(foundMovie.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MovieDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
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
    @Operation(summary = "Update a movie")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MovieDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
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
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204_WITHOUT_DATA),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<Void> deleteMovieById(@PathVariable Long id) {
        log.info("Delete Movie by passing ID, where ID is:{}", id);
        movieService.deleteMovieById(id);
        log.debug("Movie with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
