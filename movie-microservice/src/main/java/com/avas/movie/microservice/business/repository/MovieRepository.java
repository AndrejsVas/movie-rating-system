package com.avas.movie.microservice.business.repository;

import com.avas.library.business.repository.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);

    @Query("select count(c) from Movie p join p.movieLikes c where p = ?1")
    Long countChildrenByParent(Movie movie);
}
