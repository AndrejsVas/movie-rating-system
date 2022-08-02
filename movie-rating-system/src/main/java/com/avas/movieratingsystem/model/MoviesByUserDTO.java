package com.avas.movieratingsystem.model;

import com.avas.movieratingsystem.business.repository.model.MovieType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoviesByUserDTO {

    private Long id;
    private String title;
    private String description;
    private MovieType movieType;
}
