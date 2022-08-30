package com.avas.movietype.microservice.business.repository;

import main.com.avas.library.business.repository.model.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    boolean
    existsByType(String type);
}
