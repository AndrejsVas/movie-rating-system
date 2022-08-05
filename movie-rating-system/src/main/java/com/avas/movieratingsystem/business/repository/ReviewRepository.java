package com.avas.movieratingsystem.business.repository;

import com.avas.movieratingsystem.business.repository.model.Review;
import com.avas.movieratingsystem.business.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findReviewByUserId(User user);
    boolean existsByMovieIdAndUserId(Long movieId, Long userId);

}
