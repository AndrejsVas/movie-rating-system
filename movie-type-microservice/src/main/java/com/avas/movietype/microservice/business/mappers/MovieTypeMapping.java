package com.avas.movietype.microservice.business.mappers;


import com.avas.movietype.microservice.business.repository.model.Movie;
import com.avas.movietype.microservice.model.MovieTypeDTO;
import com.avas.movietype.microservice.business.repository.model.MovieType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieTypeMapping {

    @Mappings({
            @Mapping(source = "movies", target = "movieIds", qualifiedByName = "moviesToMoviesIdLong")
    })
    MovieTypeDTO mapMovieTypeToMovieTypeDto(MovieType movieEntity);

    @Mappings({
            @Mapping(source = "movieIds", target = "movies", qualifiedByName = "movieIdLongToMovie")
    })
    MovieType mapMovieTypeDtoToMovieType(MovieTypeDTO movieDto);

    List<MovieTypeDTO> mapMovieTypeListToMovieTypeListDto(List<MovieType> movieEntities);

    List<MovieType> mapMovieTypeListDtoToMovieTypeList(List<MovieTypeDTO> movieEntities);

    @Named("moviesToMoviesIdLong")
    default List<Long> moviesToMoviesIdLong(List<Movie> listOfMovies) {
        return listOfMovies.stream()
                .map(Movie::getId).collect(Collectors.toList());
    }

    @Named("movieIdLongToMovie")
    default List<Movie> movieIdLongToMovie(List<Long> listOfMovieIds) {
        //TODO: Maybe improve in some way so its functional.
        //TODO 2: Check other CREATE methods for other services
        if(listOfMovieIds == null)
            return new ArrayList<>();
        return listOfMovieIds.stream()
                .map(Movie::new).collect(Collectors.toList());
    }

}