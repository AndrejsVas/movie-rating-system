package com.avas.movieratingsystem.business.service.impl;
import com.avas.movieratingsystem.business.mappers.MovieMapping;
import com.avas.movieratingsystem.business.repository.MovieRepository;
import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.model.MovieDTO;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.avas.movieratingsystem.test.data.MovieTestData.createMovieDTO;
import static com.avas.movieratingsystem.test.data.MovieTestData.createMovieDtoList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @Autowired
    private MovieMapping movieMapping;
    @Mock
    private MovieMapping mockMovieMapping;

    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    @DisplayName("Testing retrieval of all interns")
    public void testGetAllInternsSuccessfully() {
        List<MovieDTO> movieDTOList =createMovieDtoList();
        List<Movie> movieList = movieMapping.mapMovieDtoListToMovieList(movieDTOList);
        when(movieRepository.findAll()).thenReturn(movieList);
        when(mockMovieMapping.mapMovieListToMovieListDto(movieList)).thenReturn(movieDTOList);
        List<MovieDTO> movieDTOListReturned = movieService.getAllMovies();
        Assertions.assertTrue(movieDTOList.equals(movieDTOListReturned));
        verify(movieRepository, times(1)).findAll();
    }
}
