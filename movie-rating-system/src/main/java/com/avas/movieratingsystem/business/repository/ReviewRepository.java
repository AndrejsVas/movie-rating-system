package com.avas.movieratingsystem.business.repository;

import com.avas.movieratingsystem.business.repository.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
