package com.avas.movieratingsystem.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserReviewDTO {

    private Long id;
    private String textReview;
    private int score;
    private LocalDate reviewDate;
}
