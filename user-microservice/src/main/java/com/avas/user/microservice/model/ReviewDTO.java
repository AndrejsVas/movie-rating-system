package com.avas.user.microservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {

    private Long id;
    private String textReview;
    private int score;
    private LocalDate reviewDate;
    private Long userId;
    private Long movieId;

}
