package com.avas.review.microservice.business.mappers;

import com.avas.review.microservice.business.repository.model.MovieType;
import com.avas.review.microservice.model.MovieTypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieTypeMapping {
    MovieTypeDTO mapMovieTypeToMovieTypeDto(MovieType movieEntity);

    MovieType mapMovieTypeDtoToMovieType(MovieTypeDTO movieDto);

    List<MovieTypeDTO> mapMovieTypeListToMovieTypeListDto(List<MovieType> movieEntities);

    List<MovieType> mapMovieTypeListDtoToMovieTypeList(List<MovieTypeDTO> movieEntities);


}
