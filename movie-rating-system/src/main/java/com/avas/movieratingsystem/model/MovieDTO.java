package com.avas.movieratingsystem.model;


import com.avas.movieratingsystem.business.repository.model.MovieType;
import com.avas.movieratingsystem.business.repository.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {

    private Long id;
    private String title;
    private String description;
    private List<Review> reviewIds;
    private MovieType movieType;
}
