package com.avas.movieratingsystem.business.mappers;

import com.avas.movieratingsystem.business.repository.model.Movie;
import com.avas.movieratingsystem.business.repository.model.MovieType;
import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.model.MovieDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapping {

    @Mappings({
            @Mapping(source = "reviewIds", target = "reviewIds", qualifiedByName = "reviewIdsToReviewIdsLong"),
            @Mapping(source = "movieType", target = "movieType", qualifiedByName = "movieTypeToMovieTypeString")

    })
    MovieDTO mapMovieToMovieDto(Movie movieEntity);

    @Mappings({
            @Mapping(source = "reviewIds", target = "reviewIds", qualifiedByName = "reviewIdsLongToReviewIds"),
            @Mapping(source = "movieType", target = "movieType", qualifiedByName = "movieTypeStringToMovieType")

    })
    Movie mapMovieDtoToMovie(MovieDTO movieDto);

    List<MovieDTO> mapMovieListToMovieListDto(List<Movie> movieEntities);

    @Named("reviewIdsToReviewIdsLong")
    default List<Long> reviewIdsToReviewIdsLong(List<Review> listOfReviewIds) {
        return listOfReviewIds.stream()
                .map(Review::getId).collect(Collectors.toList());
    }

    @Named("reviewIdsLongToReviewIds")
    default List<Review> reviewIdsLongToReviewIds(List<Long> listOfReviewIdsLong) {
        return listOfReviewIdsLong.stream()
                .map(Review::new).collect(Collectors.toList());
    }

    @Named("movieTypeToMovieTypeString")
    default String movieTypeStringToMovieType(MovieType movieType) {
        return movieType.getType();
    }

    @Named("movieTypeStringToMovieType")
    default MovieType movieTypeStringToMovieType(String movieType) {
        return new MovieType(movieType);
    }

}
