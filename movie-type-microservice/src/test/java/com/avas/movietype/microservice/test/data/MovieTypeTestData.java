package com.avas.movietype.microservice.test.data;

import main.com.avas.library.model.MovieTypeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovieTypeTestData {

    private static final String[] randomMovieTypeList = {"sci-fi", "action", "comedy"};

    private static final Random userTypeId = new Random();
    private static final Random randomMovieType = new Random();


    public static MovieTypeDTO createMovieTypeDto() {
        MovieTypeDTO movieTypeDTO = new MovieTypeDTO();
        movieTypeDTO.setId(userTypeId.nextLong());
        movieTypeDTO.setType(randomMovieTypeList[randomMovieType.nextInt(randomMovieTypeList.length)]);

        return movieTypeDTO;
    }
    public static MovieTypeDTO createMovieTypeDtoPredefined() {
        MovieTypeDTO movieTypeDTO = new MovieTypeDTO();
        movieTypeDTO.setId(1L);
        movieTypeDTO.setType("action");

        return movieTypeDTO;
    }

    public static List<MovieTypeDTO> createMovieTypeDtoList() {
        return Arrays.asList(createMovieTypeDto(), createMovieTypeDto(), createMovieTypeDto(), createMovieTypeDto(), createMovieTypeDto());
    }
    public static List<MovieTypeDTO> createMovieTypeDtoListPredefined() {
        return Arrays.asList(createMovieTypeDtoPredefined(), createMovieTypeDtoPredefined(), createMovieTypeDtoPredefined());
    }
}
