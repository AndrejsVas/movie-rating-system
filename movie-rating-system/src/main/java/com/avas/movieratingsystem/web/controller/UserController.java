package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.swagger.HTMLResponseMessages;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;

import com.sun.org.apache.regexp.internal.RE;
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
@Tag(name="User controller", description= "A controller for operations with the user database")
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @Operation(summary = "Retrieve a list of all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = UserDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<UserDTO>> getAllUsers() {

        List<UserDTO> userReviews = userService.getAllUsers();
        return ResponseEntity.ok(userReviews);
    }


    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> foundUserDto = userService.findUserById(id);
        log.info("User found : {}", foundUserDto.get());
        return new ResponseEntity<>(foundUserDto.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        UserDTO savedUser = userService.createUser(userDTO);
        log.debug("New user is created : {}", userDTO);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id,
                                              @RequestBody UserDTO modifiedUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        UserDTO returnedUserDto = userService.updateUser(modifiedUserDto, id);
        log.debug("User with id: {} is now :{}", id, returnedUserDto);
        return new ResponseEntity<>(returnedUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204_WITHOUT_DATA),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) {
        log.info("Delete User  by passing ID, where ID is:{}", id);
        userService.deleteUserById(id);
        log.debug("User with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @GetMapping("/{id}/movies")
    @Operation(summary = "Retrieve all movies reviewed by a user with provided user id",
            description = "This will be moved to the movie controller in the future microservice version" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = MovieDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    public ResponseEntity<List<MovieDTO>> getAllMoviesReviewedByUser(@PathVariable Long id) {
        List<MovieDTO> movieReviews = userService.getAllMoviesReviewedByUserById(id);
        log.info("Returning all movies reviewed by user with id:{}", id);
        return new ResponseEntity<>(movieReviews, HttpStatus.OK);

    }
    @GetMapping("/{id}/reviews")
    @Operation(summary = "Retrieve all reviews reviewed by a user with provided user id",
            description = "This will be moved to the review controller in the future microservice version" )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = ReviewDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    public ResponseEntity<List<ReviewDTO>> getAllReviewsMadeByUser(@PathVariable Long id) {
        List<ReviewDTO> userReviews = userService.getAllReviewsMadeByUserById(id);
        log.info("Returning all user review for user with id:{}", id);
        return new ResponseEntity<>(userReviews, HttpStatus.OK);

    }

}
