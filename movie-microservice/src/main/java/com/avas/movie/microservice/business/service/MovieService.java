package com.avas.movie.microservice.business.service;


import com.avas.library.model.MovieDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MovieService {

    List<MovieDTO>  getTopTenMovies();
    List<MovieDTO> getAllMovies();
    Optional<MovieDTO> findMovieById(Long id);
    void deleteMovieById(Long id);
    MovieDTO createMovie(MovieDTO newMovie);
    MovieDTO updateMovieById(MovieDTO modifyExistingMovie, Long id);

}
