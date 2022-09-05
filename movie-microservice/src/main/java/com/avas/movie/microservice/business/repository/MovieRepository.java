package com.avas.movie.microservice.business.repository;

import com.avas.library.business.repository.model.Movie;
import com.avas.library.business.repository.model.MovieType;
import com.avas.library.business.repository.model.Review;
import com.avas.library.business.repository.model.User;
import com.avas.library.model.MovieDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    boolean existsByTitle(String title);
    @Query(value = "SELECT * FROM movie ORDER BY RAND() Limit 1" , nativeQuery = true)
    Movie findRandomMovie();
    List<Movie> findMovieByMovieType(MovieType movieType);

    @Query("select count(c) from Movie p join p.movieLikes c where p = ?1")
    Long countChildrenByParent(Movie movie);
}
