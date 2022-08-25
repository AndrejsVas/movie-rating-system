package com.avas.movieratingsystem.web.controller;


import com.avas.movieratingsystem.business.service.UserLikeService;
import com.avas.movieratingsystem.model.UserDTO;
import com.avas.movieratingsystem.model.UserLikeDTO;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@Tag(name="User like controller", description= "A controller for operations with the user like database")
@RequestMapping("api/v1/like")
public class UserLikeController {

    @Autowired
    UserLikeService userLikeService;

    @GetMapping("/user/{userId}")
    @Operation(summary = "Retrieve a list of user likes by user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = UserLikeDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<UserLikeDTO>> getAllUserLikes(@PathVariable Long userId) {
        List<UserLikeDTO> userLikes = userLikeService.getAllUserLikes(userId);
        log.info("User with id:{} has {} likes", userId, userLikes.size());
        return new ResponseEntity<>(userLikes, HttpStatus.OK);
    }

    @GetMapping("/review/{reviewId}")
    @Operation(summary = "Retrieve a list of all likes for a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = UserLikeDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<UserLikeDTO>> getAllLikesForReview(@PathVariable Long reviewId) {
        List<UserLikeDTO> userLikes = userLikeService.getAllLikesForAReview(reviewId);
        log.info("Review with id:{} has {} likes", reviewId, userLikes.size());
        return new ResponseEntity<>(userLikes, HttpStatus.OK);
    }

    @PutMapping("/review/{reviewId}/reviewer/{userId}")
    @Operation(summary = "Toggles the like status for a specific review and user combination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserLikeDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<UserLikeDTO> toggleReviewLike(@PathVariable Long reviewId, @PathVariable Long userId) {
        return userLikeService.toggleReviewLike(reviewId, userId)
                .map(likeDTO -> new ResponseEntity<>(likeDTO, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.OK));
    }


}
