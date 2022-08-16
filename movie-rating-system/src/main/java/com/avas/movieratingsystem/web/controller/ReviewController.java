package com.avas.movieratingsystem.web.controller;


import com.avas.movieratingsystem.business.service.ReviewService;
import com.avas.movieratingsystem.model.ReviewDTO;
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
@RequestMapping("api/v1/review")
public class ReviewController {



    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        List<ReviewDTO> reviewList = reviewService.getAllReviews();
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> getReviewById(@PathVariable Long id) {
        Optional<ReviewDTO> foundReview = reviewService.findReviewById(id);
        log.info("Review found : {}", foundReview.get());
        return new ResponseEntity<>(foundReview.get(), HttpStatus.ACCEPTED);
    }

    @PostMapping
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
    public ResponseEntity<ReviewDTO> deleteReviewById(@PathVariable Long id) {
        log.info("Delete Review by passing ID, where ID is:{}", id);
        Optional<ReviewDTO> reviewDtoFound = reviewService.findReviewById(id);
        if (!(reviewDtoFound.isPresent())) {
            log.warn("Review for delete with id {} is not found.", id);
            return ResponseEntity.notFound().build();
        }
        reviewService.deleteReviewById(id);
        log.debug("Review with id {} is deleted", id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
