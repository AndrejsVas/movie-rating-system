package com.avas.movieratingsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieTypeDTO {

    private Long id;
    private String type;
    private List<Long> movieIds;

}
