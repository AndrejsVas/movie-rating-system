package com.avas.movieratingsystem.business.repository;

import com.avas.movieratingsystem.business.repository.model.MovieType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieTypeRepository extends JpaRepository<MovieType, Long> {
    boolean
    existsByType(String type);
}
