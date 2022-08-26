package com.avas.user.like.microservice.web.controller;

import com.avas.user.like.microservice.business.exceptions.ResourceNotFoundException;
import com.avas.user.like.microservice.business.service.UserLikeService;
import com.avas.user.like.microservice.controller.UserLikeController;
import com.avas.user.like.microservice.model.UserLikeDTO;
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

import static com.avas.user.like.microservice.test.data.UserLikeTestData.createUserLikeDTOPredefined;
import static com.avas.user.like.microservice.test.data.UserLikeTestData.createUserLikeDtoListPredefined;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserLikeController.class)
public class UserLikeControllerTest {
    public static String URL = "/api/v1/like";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserLikeController userLikeController;

    @MockBean
    private UserLikeService userLikeService;


    private List<UserLikeDTO> userLikeDTOListPredefined;
    private UserLikeDTO userLikeDTO;

    @BeforeEach
    public void beforeEach() {
        this.userLikeDTOListPredefined = createUserLikeDtoListPredefined();
        this.userLikeDTO = createUserLikeDTOPredefined();
    }

    @Test
    @DisplayName("Test endpoint to find all Likes for a user succesfully")
    public void findAllLikesUser() throws Exception {
        when(userLikeService.getAllUserLikes(anyLong())).thenReturn(userLikeDTOListPredefined);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reviewId").value(1L))
                .andExpect(status().isOk());
        verify(userLikeService, times(1)).getAllUserLikes(1L);
    }


    @Test
    @DisplayName("Test endpoint to find empty likes list for a user")
    public void findAllLikesEmptyUser() throws Exception {
        when(userLikeService.getAllUserLikes(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/user/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userLikeService, times(1)).getAllUserLikes(1L);

    }

    @Test
    @DisplayName("Test endpoint to find all likes for a review ")
    public void findAllLikesReview() throws Exception {
        when(userLikeService.getAllLikesForAReview(anyLong())).thenReturn(userLikeDTOListPredefined);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/review/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].reviewId").value(1L))
                .andExpect(status().isOk());
        verify(userLikeService, times(1)).getAllLikesForAReview(1L);
    }

    @Test
    @DisplayName("Test endpoint to find empty like list for a review")
    public void findAllLikesEmptyReview() throws Exception {
        when(userLikeService.getAllLikesForAReview(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/review/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userLikeService, times(1)).getAllLikesForAReview(1L);

    }

    @Test
    @DisplayName("Toggle like from dislike to like or just like a review")
    public void toggleLikeFromDislikeToLike() throws Exception {
        when(userLikeService.toggleReviewLike(anyLong(), anyLong())).thenReturn(Optional.of(userLikeDTO));
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/review/1/reviewer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewId").value(1L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Toggle like from like to dislike")
    public void toggleLikeFromLikeToDislike() throws Exception {
        when(userLikeService.toggleReviewLike(anyLong(), anyLong())).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/review/1/reviewer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("User tries to like a non existing review or user does not exist")
    public void toggleLikeFail() throws Exception {
        when(userLikeService.toggleReviewLike(anyLong(), anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/review/1/reviewer/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
    }


}
