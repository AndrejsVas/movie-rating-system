package com.avas.review.microservice.test.data;

import main.java.com.avas.library.model.MovieDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovieTestData {

    private static final String[] movieTypeRandomList = {"Horror", "Comedy", "Fantasy", "Sci-fi"
            , "Action", "Adventure", "Anime"};
    private static final String[] movieTitleRandomList = {"Epic Testing Saga", "Revenge of the Mock", "Mocking 24/7",
            "Junit:50tests", "Oops I tested my neighbour", "Never too late to test", "Tests before rests"};

    private static final Random movieId = new Random() ;
    private static final Random titles = new Random();
    private static final Random reviewIds = new Random();
    private static final Random movieTypes = new Random();
    public static MovieDTO createMovieDTO(){
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(movieId.nextLong());
        movieDTO.setTitle(movieTypeRandomList[titles.nextInt(movieTypeRandomList.length)]);
        movieDTO.setReviewIds(Arrays.asList(reviewIds.nextLong(),reviewIds.nextLong(),reviewIds.nextLong()));
        movieDTO.setMovieType(movieTypeRandomList[movieTypes.nextInt(movieTypeRandomList.length)]);
        return movieDTO;
    }

    public static MovieDTO createMovieDtoPredefined() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setId(1L);
        movieDTO.setDescription("best movie since the godfather");
        movieDTO.setTitle("Testing Returns 3d");
        return movieDTO;
    }
    public static List<MovieDTO> createMovieDtoList(){
        return Arrays.asList(createMovieDTO(), createMovieDTO(), createMovieDTO(),createMovieDTO(),createMovieDTO());
    }
    public static List<MovieDTO> createMovieDtoListPredefined() {
        return Arrays.asList(createMovieDtoPredefined(), createMovieDtoPredefined(), createMovieDtoPredefined());
    }

}
