package com.avas.review.microservice.web.controller;

import com.avas.review.microservice.business.service.ReviewService;
import com.avas.review.microservice.controller.ReviewController;
import com.avas.review.microservice.test.data.ReviewTestData;
import com.fasterxml.jackson.databind.ObjectMapper;
import main.com.avas.library.business.exceptions.ResourceAlreadyExists;
import main.com.avas.library.business.exceptions.ResourceConflict;
import main.com.avas.library.business.exceptions.ResourceNotFoundException;
import main.com.avas.library.model.ReviewDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {
    public static String URL = "/api/v1/review";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ReviewController movieController;

    @MockBean
    private ReviewService reviewService;

    private List<ReviewDTO> reviewDTOListPredefined;
    private ReviewDTO reviewDTO;

    @BeforeEach
    public void beforeEach() {
        this.reviewDTOListPredefined = ReviewTestData.createReviewDtoListPredefined();
        this.reviewDTO = ReviewTestData.createReviewDtoPredefined();
    }

    @Test
    @DisplayName("Test endpoint to find all Reviews succesfully")
    public void findAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(reviewDTOListPredefined);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(7))
                .andExpect(status().isOk());
        verify(reviewService, times(1)).getAllReviews();
    }


    @Test
    @DisplayName("Test endpoint to find empty Reviews list")
    public void findAllReviewsEmpty() throws Exception {
        when(reviewService.getAllReviews()).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(reviewService, times(1)).getAllReviews();

    }

    @Test
    @DisplayName("Test endpoint to find a non existing Review by id")
    public void findNonExistingReviewById() throws Exception {
        when(reviewService.findReviewById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(reviewService, times(1)).findReviewById(1L);
    }

    @Test
    @DisplayName("Test endpoint to successfully finding a Review by id")
    public void findReviewById() throws Exception {
        when(reviewService.findReviewById(anyLong())).thenReturn(Optional.of(reviewDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(7))
                .andExpect(status().isOk());
        verify(reviewService, times(1)).findReviewById(1L);
    }


    @Test
    @DisplayName("Test endpoint to create Review")
    public void createAReview() throws Exception {
        reviewDTO.setId(null);

        when(reviewService.createReview(reviewDTO)).thenReturn(reviewDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(7))
                .andExpect(status().isCreated());

        verify(reviewService, times(1)).createReview(reviewDTO);


    }

    @Test
    @DisplayName("Test endpoint to create a duplicate  Movie")
    public void createAReviewDuplicate() throws Exception {
        reviewDTO.setId(null);

        when(reviewService.createReview(reviewDTO)).thenThrow(ResourceAlreadyExists.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceAlreadyExists))
                .andExpect(status().isConflict());

        verify(reviewService, times(1)).createReview(reviewDTO);
    }

    @Test
    @DisplayName("Test endpoint to update a Movie")
    public void updateAReview() throws Exception {
        when(reviewService.updateReviewById(reviewDTO, 1L)).thenReturn(reviewDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.score").value(7))
                .andExpect(status().isAccepted());
        verify(reviewService, times(1)).updateReviewById(reviewDTO, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a Review which does not exist")
    public void updateANonExistingReview() throws Exception {
        when(reviewService.updateReviewById(reviewDTO, 1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(reviewService, times(1)).updateReviewById(reviewDTO, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a Review with taken movie id and user id combination")
    public void updateANonReviewWithTaken() throws Exception {
        when(reviewService.updateReviewById(reviewDTO, 1L)).thenThrow(ResourceConflict.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(reviewDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceConflict))
                .andExpect(status().isConflict());
        verify(reviewService, times(1)).updateReviewById(reviewDTO, 1L);
    }

    @Test
    @DisplayName("Test endpoint to successfully deleting a Review by id")
    public void deleteReviewById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(reviewService, times(1)).deleteReviewById(1L);
    }


}
