package com.avas.movieratingsystem.business.mappers;

import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.business.repository.model.User;
import com.avas.movieratingsystem.model.MovieDTO;
import com.avas.movieratingsystem.model.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapping {

    MovieDTO mapMovieToMovieDto(Movie movieEntity);
    Movie mapMovieDtoToMovie(MovieDTO movieDto);

    List<MovieDTO> mapMovieListToMovieListDto(List<Movie> movieEntities);
}
