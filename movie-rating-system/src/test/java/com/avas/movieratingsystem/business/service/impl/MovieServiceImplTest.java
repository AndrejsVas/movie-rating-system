package com.avas.movieratingsystem.business.service.impl;

import com.avas.movieratingsystem.business.exceptions.ResourceNotFoundException;
import com.avas.movieratingsystem.business.mappers.MovieMapping;
import com.avas.movieratingsystem.business.repository.MovieRepository;
import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.model.MovieDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.avas.movieratingsystem.test.data.MovieTestData.createMovieDTO;
import static com.avas.movieratingsystem.test.data.MovieTestData.createMovieDtoList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
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

    @Spy
    @InjectMocks
    private MovieServiceImpl movieService;

    @Test
    @DisplayName("Testing retrieval of all Movies")
    public void testMoviesSuccessfully() {
        List<MovieDTO> movieDTOList =createMovieDtoList();
        List<Movie> movieList = movieMapping.mapMovieDtoListToMovieList(movieDTOList);
        when(movieRepository.findAll()).thenReturn(movieList);
        when(mockMovieMapping.mapMovieListToMovieListDto(movieList)).thenReturn(movieDTOList);
        List<MovieDTO> movieDTOListReturned = movieService.getAllMovies();
        Assertions.assertTrue(movieDTOList.equals(movieDTOListReturned));
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing retrieval of empty Movie list")
    public void testGetAllMoviesEmpty(){
        List<MovieDTO> emptyDtoList = new ArrayList<MovieDTO>();
        List<Movie> emptyMovieList = movieMapping.mapMovieDtoListToMovieList(emptyDtoList);
        when(movieRepository.findAll()).thenReturn(emptyMovieList);
        when(mockMovieMapping.mapMovieListToMovieListDto(emptyMovieList)).thenReturn(emptyDtoList);
        List<MovieDTO> movieDTOListReturned = movieService.getAllMovies();
        Assertions.assertEquals(movieDTOListReturned.size(),0);
        verify(movieRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Testing deleting movie by id")
    public void testDeletingMovieById(){
        MovieDTO movieDTO = createMovieDTO();
        doReturn(Optional.of(movieDTO)).when(movieService).findMovieById(1L);
        movieService.deleteMovieById(1L);
        System.out.println("test");
        verify(movieRepository, times(1)).deleteById(anyLong());
    }

    @Test
    @DisplayName("Testing when id is not null")
    public void testDeleteByIdNull(){
        MovieDTO movieDTO = createMovieDTO();
        doReturn(Optional.empty()).when(movieService).findMovieById(1L);
        Assertions.assertThrows(ResourceNotFoundException.class,() -> movieService.deleteMovieById(1L));
    }

}
