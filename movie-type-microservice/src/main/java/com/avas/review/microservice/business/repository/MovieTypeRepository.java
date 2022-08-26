package com.avas.review.microservice.business.repository;

import com.avas.review.microservice.business.repository.model.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    boolean
    existsByType(String type);
}
