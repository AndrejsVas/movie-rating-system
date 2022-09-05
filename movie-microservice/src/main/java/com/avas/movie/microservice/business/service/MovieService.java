package com.avas.movie.microservice.business.service;


import com.avas.library.model.MovieDTO;
import com.avas.library.model.MovieTypeDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {

    List<MovieDTO>  getTopTenMovies();
    MovieDTO getRandomMovie();
    List<MovieDTO> getMovieOfAGenre(MovieTypeDTO movieGenre);
    List<MovieDTO> getAllMovies();
    Optional<MovieDTO> findMovieById(Long id);
    void deleteMovieById(Long id);
    MovieDTO createMovie(MovieDTO newMovie);
    MovieDTO updateMovieById(MovieDTO modifyExistingMovie, Long id);

}
