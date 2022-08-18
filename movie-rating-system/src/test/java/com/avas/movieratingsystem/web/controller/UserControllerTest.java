package com.avas.movieratingsystem.web.controller;

import com.avas.movieratingsystem.business.exceptions.ResourceAlreadyExists;
import com.avas.movieratingsystem.business.exceptions.ResourceConflict;
import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.service.UserService;
import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.ReviewDTO;
import com.avas.movieratingsystem.model.UserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import static com.avas.movieratingsystem.test.data.MovieTestData.createMovieDtoListPredefined;
import static com.avas.movieratingsystem.test.data.ReviewTestData.createReviewDtoListPredefined;
import static com.avas.movieratingsystem.test.data.UserTestData.createUserDtoListPredefined;
import static com.avas.movieratingsystem.test.data.UserTestData.createUserDtoPredefined;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    public static String URL = "/api/v1/user";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    @DisplayName("Test endpoint to find all users succesfully")
    public void findAllInterns() throws Exception {
        List<UserDTO> listOfUserDto = createUserDtoListPredefined();
        when(userService.getAllUsers()).thenReturn(listOfUserDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].email").value("testEmail@mail.com"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    @DisplayName("Test endpoint to successfully finding a user by id")
    public void findUserById() throws Exception {
        UserDTO userDTO = createUserDtoPredefined();
        when(userService.findUserById(anyLong())).thenReturn(Optional.of(userDTO));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("testEmail@mail.com"))
                .andExpect(status().isOk());
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find a non existing user by id")
    public void findNonExistingUserById() throws Exception {
        when(userService.findUserById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).findUserById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find empty user list")
    public void findAllUsersEmpty() throws Exception {
        when(userService.getAllUsers()).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).getAllUsers();

    }

    @Test
    @DisplayName("Test endpoint to create user")
    public void createAUser() throws Exception {
        UserDTO userDTO = createUserDtoPredefined();
        userDTO.setId(null);

        when(userService.createUser(userDTO)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tester"));

        verify(userService, times(1)).createUser(userDTO);


    }

    @Test
    @DisplayName("Test endpoint to create a duplicate  user")
    public void createAUserDuplicate() throws Exception {
        UserDTO userDTO = createUserDtoPredefined();
        userDTO.setId(null);

        when(userService.createUser(userDTO)).thenThrow(ResourceAlreadyExists.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(userService, times(1)).createUser(userDTO);
    }

    @Test
    @DisplayName("Test endpoint to update a user")
    public void updateAUser() throws Exception {
        UserDTO userDTO = createUserDtoPredefined();
        when(userService.updateUser(userDTO, 1L)).thenReturn(userDTO);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("tester"));
        verify(userService, times(1)).updateUser(userDTO, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a user which does not exist")
    public void updateANonExistingUser() throws Exception {
        UserDTO userDTO = createUserDtoPredefined();
        when(userService.updateUser(userDTO, 1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).updateUser(userDTO, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a user with taken email")
    public void updateANonUserWithTakenEmail() throws Exception {
        UserDTO userDTO = createUserDtoPredefined();
        when(userService.updateUser(userDTO, 1L)).thenThrow(ResourceConflict.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(userDTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceConflict))
                .andExpect(status().isConflict());
        verify(userService, times(1)).updateUser(userDTO, 1L);
    }

    @Test
    @DisplayName("Test endpoint to successfully deleting a user by id")
    public void deleteUserById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(userService, times(1)).deleteUserById(1L);
    }


    @Test
    @DisplayName("Test endpoint to find all movies reviewed by user")
    public void findAllMoviesReviewedByUser() throws Exception {
        List<MovieDTO> listOfMovieDto = createMovieDtoListPredefined();
        when(userService.getAllMoviesReviewedByUserById(anyLong())).thenReturn(listOfMovieDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value("Testing Returns 3d"))
                .andExpect(status().isOk());
        verify(userService, times(1)).getAllMoviesReviewedByUserById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find empty movie list")
    public void findAllMoviesReviewedEmpty() throws Exception {
        when(userService.getAllMoviesReviewedByUserById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1/movies")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).getAllMoviesReviewedByUserById(1L);

    }

    @Test
    @DisplayName("Test endpoint to find all reviews made by user")
    public void findAllReviewesMadeByUser() throws Exception {
        List<ReviewDTO> listOfReviewsDto = createReviewDtoListPredefined();
        when(userService.getAllReviewsMadeByUserById(anyLong())).thenReturn(listOfReviewsDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(7))
                .andExpect(status().isOk());
        verify(userService, times(1)).getAllReviewsMadeByUserById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find empty review list of user")
    public void findAllReviewsMadeByUserEmpty() throws Exception {
        when(userService.getAllReviewsMadeByUserById(anyLong())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).getAllReviewsMadeByUserById(1L);

    }

}
