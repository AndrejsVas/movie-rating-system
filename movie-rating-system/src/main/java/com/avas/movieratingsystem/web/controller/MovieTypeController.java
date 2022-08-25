package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.service.MovieTypeService;
import com.avas.movieratingsystem.model.MovieTypeDTO;
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
@Tag(name="Movie type controller", description= "A controller for operations with the movie type database")
@RequestMapping("api/v1/movie_type")
public class MovieTypeController {

    @Autowired
    MovieTypeService movieTypeService;

    @GetMapping
    @Operation(summary = "Retrieve a list of all movie types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = MovieTypeDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<MovieTypeDTO>> getAllMovieTypes() {
        List<MovieTypeDTO> movieTypeList = movieTypeService.getAllMovieTypes();
        return ResponseEntity.ok(movieTypeList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific movie by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MovieTypeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<MovieTypeDTO> getMovieType(@PathVariable Long id) {
        Optional<MovieTypeDTO> foundMovieType = movieTypeService.findMovieTypeById(id);
        log.info("Movie type found : {}", foundMovieType.get());
        return new ResponseEntity<>(foundMovieType.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a movie type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MovieTypeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
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
    @Operation(summary = "Update a movie type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MovieTypeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.OK)
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
    @Operation(summary = "Delete a movie type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204_WITHOUT_DATA),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<Void> deleteMovieTypeById(@PathVariable Long id) {
        log.info("Delete Movie Type by passing ID, where ID is:{}", id);
        movieTypeService.deleteMovieTypeById(id);
        log.debug("Movie type with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
