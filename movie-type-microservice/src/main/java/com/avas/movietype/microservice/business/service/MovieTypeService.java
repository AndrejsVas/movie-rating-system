package com.avas.movietype.microservice.business.service;

import main.com.avas.library.model.MovieTypeDTO;

import java.util.List;
import java.util.Optional;

public interface MovieTypeService {

    List<MovieTypeDTO> getAllMovieTypes();

    Optional<MovieTypeDTO> findMovieTypeById(Long id);

    void deleteMovieTypeById(Long id);

    MovieTypeDTO createMovieType(MovieTypeDTO newMovieType);

    MovieTypeDTO updateMovieTypeById(MovieTypeDTO modifyExistingMovieType, Long id);
}
