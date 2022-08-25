package com.avas.movieratingsystem.web.controller;


import com.avas.movieratingsystem.business.service.ReviewService;
import com.avas.movieratingsystem.model.ReviewDTO;
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
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@Tag(name="Review controller", description= "A controller for operations with the review database")
@RequestMapping("api/v1/review")
public class ReviewController {



    @Autowired
    ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Retrieve a list of all reviews")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            array = @ArraySchema(schema= @Schema(implementation = ReviewDTO.class))
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviewList = reviewService.getAllReviews();
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a specific review by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)})
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        Optional<ReviewDTO> foundReview = reviewService.findReviewById(id);
        log.info("Review found : {}", foundReview.get());
        return new ResponseEntity<>(foundReview.get(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = HTMLResponseMessages.HTTP_201 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ReviewDTO> createReview(@RequestBody ReviewDTO reviewDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        ReviewDTO savedReview = reviewService.createReview(reviewDTO);
        log.debug("New review is created : {}", reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);

    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = HTMLResponseMessages.HTTP_200 , content = {
                    @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReviewDTO.class)
                    )
            }),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "409", description = HTMLResponseMessages.HTTP_409),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    @ResponseStatus(value = HttpStatus.OK)
    public ResponseEntity<ReviewDTO> updateReview(@PathVariable Long id,
                                                @RequestBody ReviewDTO modifiedReviewDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            log.warn("Binding result error");
            return ResponseEntity.badRequest().build();
        }
        ReviewDTO returnedReviewDto = reviewService.updateReviewById(modifiedReviewDto , id);
        log.debug("Review with id: {} is now :{}", id, returnedReviewDto);
        return new ResponseEntity<>(returnedReviewDto,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a review")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = HTMLResponseMessages.HTTP_204_WITHOUT_DATA),
            @ApiResponse(responseCode = "400", description = HTMLResponseMessages.HTTP_400),
            @ApiResponse(responseCode = "404", description = HTMLResponseMessages.HTTP_404),
            @ApiResponse(responseCode = "500", description = HTMLResponseMessages.HTTP_500)
    })
    public ResponseEntity<Void> deleteReviewById(@PathVariable Long id) {
        log.info("Delete Review by passing ID, where ID is:{}", id);
        Optional<ReviewDTO> reviewDtoFound = reviewService.findReviewById(id);
        reviewService.deleteReviewById(id);
        log.debug("Review with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
