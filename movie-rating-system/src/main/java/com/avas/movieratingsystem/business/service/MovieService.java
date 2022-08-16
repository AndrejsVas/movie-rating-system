package com.avas.movieratingsystem.business.service;

import com.avas.movieratingsystem.model.MovieDTO;

import java.util.List;
import java.util.Optional;

public interface MovieService {

    List<MovieDTO> getAllMovies();
    Optional<MovieDTO> findMovieById(Long id);
    void deleteMovieById(Long id);
    MovieDTO createMovie(MovieDTO newMovie);
    MovieDTO updateMovieById(MovieDTO modifyExistingMovie, Long id);

}
