package com.avas.movieratingsystem.test.data;

import com.avas.movieratingsystem.model.MovieTypeDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MovieTypeTestData {

    private static final String[] randomUserTypeList = {"user", "admin", "moderator"};

    private static final Random userTypeId = new Random();
    private static final Random randomUserType = new Random();


    public static MovieTypeDTO createMovieTypeDto() {
        MovieTypeDTO movieTypeDTO = new MovieTypeDTO();
        movieTypeDTO.setId(userTypeId.nextLong());
        movieTypeDTO.setType(randomUserTypeList[randomUserType.nextInt(randomUserTypeList.length)]);

        return movieTypeDTO;
    }

    public static List<MovieTypeDTO> createMovieTypeDtoList() {
        return Arrays.asList(createMovieTypeDto(), createMovieTypeDto(), createMovieTypeDto(), createMovieTypeDto(), createMovieTypeDto());
    }
}
