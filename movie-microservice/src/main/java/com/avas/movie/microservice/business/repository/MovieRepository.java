package com.avas.movie.microservice.business.repository;

import main.java.com.avas.library.business.repository.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);
}
