package com.avas.movietype.microservice.web.controller;

import com.avas.movietype.microservice.business.service.MovieTypeService;
import com.avas.movietype.microservice.controller.MovieTypeController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.avas.library.business.exceptions.ResourceAlreadyExists;
import com.avas.library.business.exceptions.ResourceConflict;
import com.avas.library.business.exceptions.ResourceNotFoundException;
import com.avas.library.model.MovieTypeDTO;
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

import static com.avas.movietype.microservice.test.data.MovieTypeTestData.createMovieTypeDtoListPredefined;
import static com.avas.movietype.microservice.test.data.MovieTypeTestData.createMovieTypeDtoPredefined;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieTypeController.class)
public class MovieTypeControllerTest {
    public static String URL = "/api/v1/movie_type";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MovieTypeController movieTypeController;

    @MockBean
    private MovieTypeService movieTypeService;

    private List<MovieTypeDTO> movieTypeDTOSPredefined;
    private MovieTypeDTO movieTypeDto;

    @BeforeEach
    public void beforeEach() {
        this.movieTypeDTOSPredefined = createMovieTypeDtoListPredefined();
        this.movieTypeDto = createMovieTypeDtoPredefined();
    }

    @Test
    @DisplayName("Test endpoint to find all MovieTypes successfully")
    public void findAllMovieTypes() throws Exception {
        when(movieTypeService.getAllMovieTypes()).thenReturn(movieTypeDTOSPredefined);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].type").value("action"))
                .andExpect(status().isOk());
        verify(movieTypeService, times(1)).getAllMovieTypes();
    }

    @Test
    @DisplayName("Test endpoint to successfully finding a MovieType by id")
    public void findMovieTypeById() throws Exception {
        when(movieTypeService.findMovieTypeById(anyLong())).thenReturn(Optional.of(movieTypeDto));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("action"))
                .andExpect(status().isOk());
        verify(movieTypeService, times(1)).findMovieTypeById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find a non existing MovieType by id")
    public void findNonExistingMovieTypeById() throws Exception {
        when(movieTypeService.findMovieTypeById(anyLong())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/1"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(movieTypeService, times(1)).findMovieTypeById(1L);
    }

    @Test
    @DisplayName("Test endpoint to find empty MovieTypes list")
    public void findAllMovieTypesEmpty() throws Exception {
        when(movieTypeService.getAllMovieTypes()).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(movieTypeService, times(1)).getAllMovieTypes();

    }

    @Test
    @DisplayName("Test endpoint to create MovieType")
    public void createAMovieType() throws Exception {
        movieTypeDto.setId(null);

        when(movieTypeService.createMovieType(movieTypeDto)).thenReturn(movieTypeDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(movieTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("action"));

        verify(movieTypeService, times(1)).createMovieType(movieTypeDto);


    }

    @Test
    @DisplayName("Test endpoint to create a duplicate  MovieType")
    public void createAMovieTypeDuplicate() throws Exception {
        movieTypeDto.setId(null);

        when(movieTypeService.createMovieType(movieTypeDto)).thenThrow(ResourceAlreadyExists.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .content(new ObjectMapper().writeValueAsString(movieTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());

        verify(movieTypeService, times(1)).createMovieType(movieTypeDto);
    }

    @Test
    @DisplayName("Test endpoint to update a MovieType")
    public void updateAMovieType() throws Exception {
        when(movieTypeService.updateMovieTypeById(movieTypeDto, 1L)).thenReturn(movieTypeDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(movieTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andExpect(MockMvcResultMatchers.jsonPath("$.type").value("action"));
        verify(movieTypeService, times(1)).updateMovieTypeById(movieTypeDto, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a MovieType which does not exist")
    public void updateANonExistingMovieType() throws Exception {
        when(movieTypeService.updateMovieTypeById(movieTypeDto, 1L)).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(movieTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceNotFoundException))
                .andExpect(status().isNotFound());
        verify(movieTypeService, times(1)).updateMovieTypeById(movieTypeDto, 1L);
    }

    @Test
    @DisplayName("Test endpoint to update a MovieType with taken email")
    public void updateANonMovieTypeWithTakenType() throws Exception {
        when(movieTypeService.updateMovieTypeById(movieTypeDto, 1L)).thenThrow(ResourceConflict.class);

        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/1")
                        .content(new ObjectMapper().writeValueAsString(movieTypeDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ResourceConflict))
                .andExpect(status().isConflict());
        verify(movieTypeService, times(1)).updateMovieTypeById(movieTypeDto, 1L);
    }

    @Test
    @DisplayName("Test endpoint to successfully deleting a MovieType by id")
    public void deleteMovieTypeById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(movieTypeService, times(1)).deleteMovieTypeById(1L);
    }


}
