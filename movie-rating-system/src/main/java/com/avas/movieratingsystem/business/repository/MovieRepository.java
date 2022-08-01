package com.avas.movieratingsystem.business.repository;

import com.avas.movieratingsystem.business.repository.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}
