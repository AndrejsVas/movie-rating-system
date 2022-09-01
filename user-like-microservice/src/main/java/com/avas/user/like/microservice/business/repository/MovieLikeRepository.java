package com.avas.user.like.microservice.business.repository;

import com.avas.library.business.repository.model.Movie;
import com.avas.library.business.repository.model.MovieLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieLikeRepository extends JpaRepository<MovieLike, Long> {
    List<MovieLike> findMovieLikeByMovieId(Movie movie);
}